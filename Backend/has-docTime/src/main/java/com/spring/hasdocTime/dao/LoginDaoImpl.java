package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.*;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.LoginInterface;
import com.spring.hasdocTime.repository.UserRepository;
import com.spring.hasdocTime.security.customUserClass.UserDetailForToken;
import com.spring.hasdocTime.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private static final long EXPIRATION_TIME = 130;  //60 seconds

    HashMap<String, String> otpMap = new LinkedHashMap<>();
    private Map<String, ScheduledFuture<?>> expirationTasks = new ConcurrentHashMap<>();

    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    public LoginDaoImpl(
            UserRepository userRepository,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            JavaMailSender javaMailSender,
            PasswordEncoder passwordEncoder
            ){
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
        Authentication auth = authenticationManager.authenticate(
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
    public Void sendEmailForForgotPassword(SendOtpEmail sendOtpEmail) {
        String otp = generateRandomOtp();

        addOtp(sendOtpEmail.getEmail(), otp);

        sendEmail(sendOtpEmail.getEmail(), otp);

        return null;
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

    @Override
    public Boolean saveNewPassword(PasswordUpdateBody passwordUpdateBody) {
        Optional<User> user = userRepository.findById(passwordUpdateBody.getId());

        if(user.isPresent()){
            if(passwordUpdateBody.getNewPassword().equals(passwordUpdateBody.getConfirmPassword())){
                user.get().setPassword(passwordEncoder.encode(passwordUpdateBody.getNewPassword()));
                userRepository.save(user.get());
                return true;
            }
        }
        return false;
    }

    private void sendEmail(String email, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("HAS - Forgot Password");
        message.setText("Kindly enter the given otp at the website: " + otp);

        javaMailSender.send(message);

    }

    private String generateRandomOtp() {

        String characters = "0123456789";
        StringBuilder otp = new StringBuilder();

        Random random = new Random();

        for(int i=0; i<OTP_LENGTH; i++){
            int index = random.nextInt(characters.length());
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
            otpMap.remove(key);;
        }, expirationTime, TimeUnit.SECONDS);

        expirationTasks.put(key, expirationTask);
    }

}
