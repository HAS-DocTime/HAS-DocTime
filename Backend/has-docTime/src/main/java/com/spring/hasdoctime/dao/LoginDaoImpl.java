package com.spring.hasdoctime.dao;

import com.spring.hasdoctime.entity.*;
import com.spring.hasdoctime.exceptionhandling.exception.MissingParameterException;
import com.spring.hasdoctime.interfaces.LoginInterface;
import com.spring.hasdoctime.repository.UserRepository;
import com.spring.hasdoctime.security.customuserclass.UserDetailForToken;
import com.spring.hasdoctime.security.jwt.JwtService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.*;


/**
 * Implementation of the LoginInterface that provides login functionality.
 */
@Service
public class LoginDaoImpl implements LoginInterface {


    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private static final int OTP_LENGTH = 6;

    HashMap<String, String> otpMap = new LinkedHashMap<>();
    private Map<String, ScheduledFuture<?>> expirationTasks = new ConcurrentHashMap<>();

    private ScheduledExecutorService scheduledExecutorService;

    private final Random random = new Random();


    @Autowired
    public LoginDaoImpl(
            UserRepository userRepository,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            JavaMailSender javaMailSender,
            PasswordEncoder passwordEncoder
            ) {
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Performs a login request for the given login details.
     *
     * @param loginDetail the login details
     * @return an AuthenticationResponse containing the generated JWT token
     * @throws MissingParameterException if any required parameter is missing
     */
    @Transactional
    @Override
    public AuthenticationResponse loginRequest(LoginDetail loginDetail) throws MissingParameterException {

        if(loginDetail.getEmail()==null || loginDetail.getEmail().equals("")){
            throw new MissingParameterException("Email");
        }
        if(loginDetail.getPassword()==null || loginDetail.getPassword().equals("")){
            throw new MissingParameterException("Password");
        }

        // Perform authentication
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                loginDetail.getEmail(),
                loginDetail.getPassword());
        authenticationManager.authenticate(
                usernamePassword
        );

        // Get user information
        var user = userRepository.findByEmail(loginDetail.getEmail()).orElseThrow();
        UserDetailForToken userDetailForToken;

        // Create user detail for token based on user role
        if(user.getRole().toString().equals("DOCTOR")){
            userDetailForToken = new UserDetailForToken(user.getEmail(), user.getDoctor().getId(), user.getRole());
        }else if(user.getRole().toString().equals("PATIENT")){
            userDetailForToken = new UserDetailForToken(user.getEmail(), user.getId(), user.getRole());
        }else{
            userDetailForToken = new UserDetailForToken(user.getEmail(), user.getAdmin().getId(), user.getRole());
        }

        // Generate JWT token
        var jwtToken = jwtService.generateToken(userDetailForToken);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public Boolean sendEmailForForgotPassword(SendOtpEmail sendOtpEmail) throws MessagingException {

        Optional<User> user = userRepository.findByEmail(sendOtpEmail.getEmail());

        if(user.isPresent()){
            String otp = generateRandomOtp();

            addOtp(sendOtpEmail.getEmail(), otp);

            sendEmail(sendOtpEmail.getEmail(), otp);
            return true;
        }

        return false;
    }

    @Override
    public Boolean otpVerification(OtpRequestBody otpRequestBody) {

        String storedOtp = otpMap.get(otpRequestBody.getEmail());

        if(storedOtp != null && storedOtp.equals(otpRequestBody.getOtp())){
            scheduleExpiration(otpRequestBody.getEmail(), 310);
            return true;
        }else{
            return false;
        }


    }

    @Transactional
    @Override
    public Boolean saveNewPassword(PasswordUpdateBody passwordUpdateBody) {
        Optional<User> user = userRepository.findByEmail(passwordUpdateBody.getEmail());

        String storedOtp = otpMap.get(passwordUpdateBody.getEmail());

        if(storedOtp != null && storedOtp.equals(passwordUpdateBody.getOtp()) && user.isPresent() && passwordUpdateBody.getPassword().equals(passwordUpdateBody.getConfirmPassword())){
                user.get().setPassword(passwordEncoder.encode(passwordUpdateBody.getPassword()));
                userRepository.save(user.get());
                otpMap.remove(passwordUpdateBody.getEmail());
                return true;
        }
        return false;
    }

    private void sendEmail(String email, String otp) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);

        String htmlContent = "<html><body>"
                + "<h2 style=\"color: #007bff;\">HAS - Forgot Password</h2>"
                + "<p>Kindly enter the given OTP at the website:</p>"
                + "<p style=\"font-weight: bold; font-size: 18px;\">" + otp + "</p>"
                + "</body></html>";

        helper.setSubject("HAS - Forgot Password");
        helper.setText(htmlContent, true);

        javaMailSender.send(message);

    }

    private String generateRandomOtp() {

        String characters = "0123456789";
        StringBuilder otp = new StringBuilder();

        for(int i=0; i<OTP_LENGTH; i++){
            int index = this.random.nextInt(characters.length());
            otp.append(characters.charAt(index));
        }

        return otp.toString();

    }

    private void addOtp(String email, String otp){
        otpMap.put(email, otp);
        scheduleExpiration(email, 130);
    }

    private void scheduleExpiration(String key, int expirationTime){

        ScheduledFuture<?> existingTask = expirationTasks.get(key);
        if(existingTask != null){
            existingTask.cancel(false);
        }
        ScheduledFuture<?> expirationTask = scheduledExecutorService.schedule(() -> {
            otpMap.remove(key);
        }, expirationTime, TimeUnit.SECONDS);

        expirationTasks.put(key, expirationTask);
    }

}
