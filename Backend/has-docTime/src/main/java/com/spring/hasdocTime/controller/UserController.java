package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.UserInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;

/**
 * The UserController class handles HTTP requests related to the User entity.
 */
@RestController
@RequestMapping("user")
@CrossOrigin(origins = "${port.address}")
public class UserController {

    private UserInterface userService;

    @Autowired
    public UserController(@Qualifier("userServiceImpl") UserInterface userService) {
        this.userService = userService;
    }

    /**
     * Retrieves all users.
     *
     * @return ResponseEntity containing a list of all users and HttpStatus.OK if successful,
     * or HttpStatus.NOT_FOUND if no users are found.
     */
    @GetMapping("")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.getAllUser();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Retrieves a user by email.
     *
     * @return ResponseEntity containing the retrieved user and HttpStatus.OK if successful.
     * @throws DoesNotExistException If the user with the given email does not exist.
     */
    @GetMapping("findByEmail")
    public ResponseEntity<User> getUserByEmail() throws DoesNotExistException {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmail(userEmail);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity containing the retrieved user and HttpStatus.OK if successful.
     * @throws AccessDeniedException If the authenticated user does not have access to the user resource.
     * @throws DoesNotExistException If the user with the given ID does not exist.
     */
    @GetMapping("{patientId}")
    public ResponseEntity<User> getUser(@PathVariable("patientId") int id) throws AccessDeniedException, DoesNotExistException {
        String authenticatedPatientId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Creates a new user.
     *
     * @param theUser The user object to create.
     * @return ResponseEntity containing the created user and HttpStatus.CREATED if successful.
     * @throws MissingParameterException If required parameters are missing in the creation request.
     * @throws DoesNotExistException     If the user with the given ID does not exist.
     */
    @PostMapping("")
    public ResponseEntity<User> createUser(@Valid @RequestBody User theUser) throws MissingParameterException, DoesNotExistException {
        User user = userService.createUser(theUser);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Updates a user.
     *
     * @param id      The ID of the user to update.
     * @param theUser The updated user object.
     * @return ResponseEntity containing the updated user and HttpStatus.OK if successful,
     * or HttpStatus.NOT_FOUND if the user does not exist.
     * @throws DoesNotExistException     If the user with the given ID does not exist.
     * @throws MissingParameterException If required parameters are missing in the update request.
     */
    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @Valid @RequestBody User theUser) throws DoesNotExistException, MissingParameterException {
        User user = userService.updateUser(id, theUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Deletes a user.
     *
     * @param id The ID of the user to delete.
     * @return ResponseEntity with HttpStatus.OK if the user is successfully deleted,
     * or HttpStatus.NOT_FOUND if the user does not exist.
     * @throws DoesNotExistException If the user with the given ID does not exist.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id) throws DoesNotExistException {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

    /**
     * Retrieves all patients.
     *
     * @return ResponseEntity containing a list of all patients and HttpStatus.OK if successful,
     * or HttpStatus.NOT_FOUND if no patients are found.
     */
    @GetMapping("patient")
    public ResponseEntity<List<User>> getPatients() {
        List<User> users = userService.getPatients();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Retrieves patients by chronic illness ID.
     *
     * @param id The ID of the chronic illness.
     * @return ResponseEntity containing a set of patients and HttpStatus.OK if successful,
     * or HttpStatus.NOT_FOUND if no patients are found.
     * @throws DoesNotExistException If the chronic illness with the given ID does not exist.
     */
    @GetMapping("patient/chronicIllness/{id}")
    public ResponseEntity<Set<User>> getPatientsByChronicIllnessId(@PathVariable("id") int id) throws DoesNotExistException {
        Set<User> users = userService.getPatientsByChronicIllnessId(id);
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
