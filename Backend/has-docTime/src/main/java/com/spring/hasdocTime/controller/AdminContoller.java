package com.spring.hasdocTime.controller;

import com.spring.hasdocTime.entity.Admin;
import com.spring.hasdocTime.interfc.AdminInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminContoller {

    @Autowired
    @Qualifier("adminServiceImpl")
    private AdminInterface adminService;

    @GetMapping("")
    public List<Admin> getAllAdmin(){
        return adminService.getAllAdmin();
    }

    @GetMapping("{id}")
    public Admin getAdmin(@PathVariable int id){
        return adminService.getAdmin(id);
    }

    @PutMapping("{id}")
    public Admin updateAdmin(@PathVariable int id, @RequestBody Admin admin){
        return adminService.updateAdmin(id, admin);
    }

    @DeleteMapping("{id}")
    public boolean deleteAdmin(@PathVariable int id){
        return adminService.deleteAdmin(id);
    }

    @PostMapping("")
    public Admin createAdmin(@RequestBody Admin admin){
        return adminService.createAdmin(admin);
    }

}
