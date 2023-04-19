/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.Doctor;
import java.util.List;

/**
 *
 * @author arpit
 */
public interface DoctorInterface {
    public Doctor createDoctor(Doctor doctor);
    public List<Doctor> getAllDoctors();
    public Doctor getDoctor(int id);
    public Doctor updateDoctor(int id, Doctor doctor);
    public Doctor deleteDoctor(int id);
}
