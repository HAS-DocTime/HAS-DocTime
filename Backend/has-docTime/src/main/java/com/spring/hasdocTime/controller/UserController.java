package com.spring.hasdocTime.controller;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.UserInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "http://192.1.200.177:4200")
public class UserController {

    private UserInterface userService;

    @Autowired
    public UserController(@Qualifier("userServiceImpl") UserInterface theUserService) {
        this.userService = theUserService;
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "name") String sortBy,     //sortBy name only
            @RequestParam(required = false) String search
    ){
        Page<User> users = userService.getAllUser(page, size, sortBy, search);
        if(users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity(users.getContent(), HttpStatus.OK);
    }
    
    @GetMapping("findByEmail")
    public ResponseEntity<User> getUserByEmail() throws DoesNotExistException{
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmail(userEmail);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("{patientId}")
    public ResponseEntity<User> getUser(@PathVariable("patientId") int id) throws AccessDeniedException, DoesNotExistException {

        String authenticatedPatientId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(id);
//        if(!authenticatedPatientId.equals(user.getEmail())){
//            throw new AccessDeniedException("You do not have access to this resource");
//        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<User> createUser(@Valid @RequestBody User theUser) throws MissingParameterException, DoesNotExistException {
        User user = userService.createUser(theUser);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @Valid @RequestBody User theUser) throws DoesNotExistException, MissingParameterException {
        User user =  userService.updateUser(id, theUser);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id) throws DoesNotExistException{
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

    @GetMapping("patient")
    public ResponseEntity<List<User>> getPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(required = false) String search
    ){
        Page<User> users = userService.getPatients(page, size, sortBy, search);
        if(users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity(users.getContent(), HttpStatus.OK);
    }

    @GetMapping("patient/chronicIllness/{id}")
    public ResponseEntity<List<User>> getPatientsByChronicIllnessId(
            @PathVariable("id") int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(required = false) String search
    ) throws DoesNotExistException {
        Page<User> result = userService.getPatientsByChronicIllnessId(id, page, size, sortBy, search);
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result.getContent());
    }
}
