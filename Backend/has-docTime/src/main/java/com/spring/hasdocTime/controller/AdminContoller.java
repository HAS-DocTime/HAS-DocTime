package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.Admin;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.AdminInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://192.1.200.177:4200")
public class AdminContoller {

    @Autowired
    @Qualifier("adminServiceImpl")
    private AdminInterface adminService;

    @GetMapping("")
    public ResponseEntity<List<Admin>> getAllAdmin(){
        List<Admin> responseAllAdmin = adminService.getAllAdmin();
        return new ResponseEntity(responseAllAdmin, HttpStatus.OK);
    }

    @GetMapping("{adminId}")
    public ResponseEntity<Admin> getAdmin(@PathVariable("adminId") int id) throws AccessDeniedException, DoesNotExistException {
//        String authenticatedAdminEmailId = SecurityContextHolder.getContext().getAuthentication().getName();
        Admin admin = adminService.getAdmin(id);

//        if(!authenticatedAdminEmailId.equals(admin.getUser().getEmail())){
//            throw new AccessDeniedException("You do not have access to this resource");
//        };
        return new ResponseEntity(admin, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable int id, @RequestBody Admin admin) throws DoesNotExistException, MissingParameterException {
        Admin responseAdmin = adminService.updateAdmin(id, admin);

        if(admin == null){
            return new ResponseEntity(responseAdmin, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(responseAdmin, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteAdmin(@PathVariable int id) throws DoesNotExistException {
        Boolean response = adminService.deleteAdmin(id);

        if(response == false){
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) throws MissingParameterException, DoesNotExistException{
        Admin responseAdmin = adminService.createAdmin(admin);
        if(responseAdmin == null){
            return new ResponseEntity(responseAdmin, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(responseAdmin, HttpStatus.OK);
    }

}
