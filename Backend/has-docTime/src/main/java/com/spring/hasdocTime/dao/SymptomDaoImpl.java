package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.Appointment;
import com.spring.hasdocTime.entity.Department;
import com.spring.hasdocTime.entity.Symptom;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.interfc.AppointmentInterface;
import com.spring.hasdocTime.interfc.DepartmentInterface;
import com.spring.hasdocTime.interfc.SymptomInterface;
import com.spring.hasdocTime.interfc.UserInterface;
import com.spring.hasdocTime.repository.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SymptomDaoImpl implements SymptomInterface {

    private SymptomRepository symptomRepository;

    private UserInterface userDao;

    private DepartmentInterface departmentDao;

    private AppointmentInterface appointmentDao;
    @Autowired
    public SymptomDaoImpl(SymptomRepository symptomRepository, @Qualifier("userDaoImpl") UserInterface userDao, @Qualifier("departmentDaoImpl") DepartmentInterface departmentDao, @Qualifier("appointmentDaoImpl") AppointmentInterface appointmentDao) {
        this.symptomRepository = symptomRepository;
        this.userDao = userDao;
        this.departmentDao = departmentDao;
        this.appointmentDao = appointmentDao;
    }
    @Override
    public Symptom getSymptom(int id) {
        Optional<Symptom> optionalSymptom = symptomRepository.findById(id);
        Symptom s = null;
        if(optionalSymptom.isPresent()){
            s = optionalSymptom.get();
        }
        return s;
    }

    @Override
    public List<Symptom> getAllSymptom() {
        return symptomRepository.findAll();
    }

    @Override
    public Symptom createSymptom(Symptom symptom) {
//        symptom.setId(0);

        // Users
        List<User> users = symptom.getUsers();
        List<User> usersWithData = new ArrayList<>();
        for(User user : users){
            User userWithData = userDao.getUser(user.getId());
            System.out.println(userWithData.getId());
            usersWithData.add(userWithData);
        }
        symptom.setUsers(usersWithData);

        // Departments
        List<Department> departments = symptom.getDepartments();
        List<Department> departmentsWithData = new ArrayList<>();
        for(Department department : departments){
            Department departmentWithData = departmentDao.getDepartment(department.getId());
            departmentsWithData.add(departmentWithData);
        }
        symptom.setDepartments(departmentsWithData);

        // Appointment
        List<Appointment> appointments = symptom.getAppointments();
        List<Appointment> appointmentsWithData = new ArrayList<>();
        for(Appointment appointment : appointments){
            Appointment appointmentWithData = appointmentDao.getAppointmentById(appointment.getId());
            appointmentsWithData.add(appointmentWithData);
        }
        symptom.setAppointments(appointmentsWithData);
        return symptomRepository.save(symptom);
    }

    @Override
    public Symptom updateSymptom(int id, Symptom symptom) {
        Optional<Symptom> optionalSymptom = symptomRepository.findById(id);
        Symptom s = null;
        if(optionalSymptom.isPresent()){
            symptom.setId(id);
            return createSymptom(symptom);
        }
        return s;
    }

    @Override
    public boolean deleteSymptom(int id) {
        Optional<Symptom> optionalSymptom = symptomRepository.findById(id);
        if(optionalSymptom.isPresent()){

            //Different Approach
//            List<Department> departments = optionalSymptom.get().getDepartments();
//            for(Department department : departments){
//                department.getSymptoms().remove(department);
//            }
//            System.out.println("department done");
//            List<User> users = optionalSymptom.get().getUsers();
//            for(User user : users){
//                user.getSymptoms().remove(user);
//            }
             // Put appointment link

//            List<User> users = optionalSymptom.get().getUsers();
//            for(User user : users){
//                user.getSymptoms().remove(user);
//            }

            symptomRepository.deleteById(optionalSymptom.get().getId());
            return true;
        }
        return false;
    }
}
