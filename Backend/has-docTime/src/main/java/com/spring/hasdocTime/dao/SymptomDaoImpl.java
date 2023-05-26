package com.spring.hasdocTime.dao;

import com.spring.hasdocTime.entity.Appointment;
import com.spring.hasdocTime.entity.Department;
import com.spring.hasdocTime.entity.Symptom;
import com.spring.hasdocTime.entity.User;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfc.AppointmentInterface;
import com.spring.hasdocTime.interfc.DepartmentInterface;
import com.spring.hasdocTime.interfc.SymptomInterface;
import com.spring.hasdocTime.interfc.UserInterface;
import com.spring.hasdocTime.repository.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Symptom getSymptom(int id) throws DoesNotExistException{
        Optional<Symptom> optionalSymptom = symptomRepository.findById(id);
        if(optionalSymptom.isEmpty()){
            throw new DoesNotExistException("Symptom");
        }
        return optionalSymptom.get();
    }

    @Override
    public Page<Symptom> getAllSymptom(int page, int size, String sortBy, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Symptom> symptoms;
        if(search != null && !search.isEmpty()){
            symptoms = symptomRepository.findAllAndNameContainsIgnoreCase(search, pageable);
        } else {
            symptoms = symptomRepository.findAll(pageable);
        }
        return symptoms;
    }

    @Override
    public Symptom createSymptom(Symptom symptom) throws DoesNotExistException, MissingParameterException {
//        symptom.setId(0);

        // Users
        if(symptom.getName()==null || symptom.getName().equals("")){
            throw new MissingParameterException("Name");
        }
        if(symptom.getUsers() != null){
            List<User> users = symptom.getUsers();
            List<User> usersWithData = new ArrayList<>();
            for(User user : users){
                User userWithData = userDao.getUser(user.getId());
                usersWithData.add(userWithData);
            }
            symptom.setUsers(usersWithData);
        }
        
        

        // Departments
        if(symptom.getDepartments() != null){
            List<Department> departments = symptom.getDepartments();
            List<Department> departmentsWithData = new ArrayList<>();
            for(Department department : departments){
                Department departmentWithData = departmentDao.getDepartment(department.getId());
                departmentsWithData.add(departmentWithData);
            }
            symptom.setDepartments(departmentsWithData);
        }
       

        // Appointment
        if(symptom.getAppointments() != null){
             List<Appointment> appointments = symptom.getAppointments();
            List<Appointment> appointmentsWithData = new ArrayList<>();
            for(Appointment appointment : appointments){
                Appointment appointmentWithData = appointmentDao.getAppointmentById(appointment.getId());
                appointmentsWithData.add(appointmentWithData);
            }
            symptom.setAppointments(appointmentsWithData);
        }
        return symptomRepository.save(symptom);
    }

    @Override
    public Symptom updateSymptom(int id, Symptom symptom) throws DoesNotExistException, MissingParameterException{
        if(symptom.getName()==null || symptom.getName().equals("")){
            throw new MissingParameterException("Name");
        }
        Optional<Symptom> optionalSymptom = symptomRepository.findById(id);
        Symptom s = null;
        if(optionalSymptom.isPresent()){
            symptom.setId(id);
            return createSymptom(symptom);
        }
        throw new DoesNotExistException("Symptom");
    }

    @Override
    public boolean deleteSymptom(int id) throws DoesNotExistException{
        Optional<Symptom> optionalSymptom = symptomRepository.findById(id);
        if(optionalSymptom.isPresent()){
            symptomRepository.deleteById(optionalSymptom.get().getId());
            return true;
        }
        throw new DoesNotExistException("Symptom");
    }
}
