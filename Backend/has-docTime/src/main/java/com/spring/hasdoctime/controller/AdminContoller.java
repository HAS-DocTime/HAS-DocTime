package com.spring.hasdoctime.controller;

import com.spring.hasdoctime.entity.Admin;
import com.spring.hasdoctime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdoctime.interfaces.AdminInterface;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * The AdminController class handles HTTP requests related to the Admin entity.
 */
@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "${port.address}")
public class AdminContoller {

    @Autowired
    @Qualifier("adminServiceImpl")
    private AdminInterface adminService;


    /**
     * Retrieves all Admin entities.
     *
     * @return ResponseEntity containing a list of Admin entities and HttpStatus.OK if successful.
     */
    @GetMapping("")
    public ResponseEntity<List<Admin>> getAllAdmin(){
        List<Admin> responseAllAdmin = adminService.getAllAdmin();
        return new ResponseEntity(responseAllAdmin, HttpStatus.OK);
    }


    /**
     * Retrieves an Admin entity by its ID.
     *
     * @param id The ID of the Admin entity to retrieve.
     * @return ResponseEntity containing the retrieved Admin entity and HttpStatus.OK if successful.
     * @throws AccessDeniedException If the authenticated admin does not have access to the requested resource.
     * @throws DoesNotExistException If the Admin entity with the given ID does not exist.
     */
    @GetMapping("{adminId}")
    public ResponseEntity<Admin> getAdmin(@PathVariable("adminId") int id) throws AccessDeniedException, DoesNotExistException {
//        String authenticatedAdminEmailId = SecurityContextHolder.getContext().getAuthentication().getName();
        Admin admin = adminService.getAdmin(id);

//        if(!authenticatedAdminEmailId.equals(admin.getUser().getEmail())){
//            throw new AccessDeniedException("You do not have access to this resource");
//        };
        return new ResponseEntity(admin, HttpStatus.OK);
    }


    /**
     * Updates an Admin entity.
     *
     * @param id    The ID of the Admin entity to update.
     * @param admin The updated Admin entity.
     * @return ResponseEntity containing the updated Admin entity and HttpStatus.OK if successful,
     * or HttpStatus.NOT_FOUND if the Admin entity was not found.
     * @throws DoesNotExistException     If the Admin entity with the given ID does not exist.
     * @throws MissingParameterException If required parameters are missing in the update request.
     */
    @PutMapping("{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable int id, @RequestBody Admin admin) throws DoesNotExistException, MissingParameterException {
        Admin responseAdmin = adminService.updateAdmin(id, admin);

        if(admin == null){
            return new ResponseEntity(responseAdmin, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(responseAdmin, HttpStatus.OK);
    }


    /**
     * Deletes an Admin entity by its ID.
     *
     * @param id The ID of the Admin entity to delete.
     * @return ResponseEntity containing a boolean indicating the success of the deletion and HttpStatus.OK if successful,
     * or HttpStatus.BAD_REQUEST if the Admin entity was not found.
     * @throws DoesNotExistException If the Admin entity with the given ID does not exist.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteAdmin(@PathVariable int id) throws DoesNotExistException {
        Boolean response = adminService.deleteAdmin(id);

        if(response == false){
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * Creates a new Admin entity.
     *
     * @param admin The Admin entity to create.
     * @return ResponseEntity containing the created Admin entity and HttpStatus.OK if successful,
     * or HttpStatus.BAD_REQUEST if the creation fails.
     * @throws MissingParameterException If required parameters are missing in the creation request.
     * @throws DoesNotExistException     If the referenced entities (e.g., User) do not exist.
     */


    @PostMapping("")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) throws MissingParameterException, DoesNotExistException{

        Admin responseAdmin = adminService.createAdmin(admin);
        if(responseAdmin == null){
            return new ResponseEntity(responseAdmin, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(responseAdmin, HttpStatus.OK);
    }

}
