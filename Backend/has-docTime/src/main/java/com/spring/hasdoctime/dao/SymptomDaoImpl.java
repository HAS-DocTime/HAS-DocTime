package com.spring.hasdoctime.dao;

import com.spring.hasdoctime.entity.Appointment;
import com.spring.hasdoctime.entity.Department;
import com.spring.hasdoctime.entity.Symptom;
import com.spring.hasdoctime.entity.User;
import com.spring.hasdoctime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdoctime.interfaces.AppointmentInterface;
import com.spring.hasdoctime.interfaces.DepartmentInterface;
import com.spring.hasdoctime.interfaces.SymptomInterface;
import com.spring.hasdoctime.interfaces.UserInterface;
import com.spring.hasdoctime.repository.SymptomRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the SymptomInterface that interacts with the SymptomRepository to perform CRUD operations on Symptom entities.
 */
@Repository
public class SymptomDaoImpl implements SymptomInterface {

    private SymptomRepository symptomRepository;

    private UserInterface userDao;

    private DepartmentInterface departmentDao;

    private AppointmentInterface appointmentDao;

    /**
     * Constructs a SymptomDaoImpl with the necessary dependencies.
     * @param symptomRepository The repository for accessing and manipulating Symptom entities.
     * @param userDao The interface for accessing User entities.
     * @param departmentDao The interface for accessing Department entities.
     * @param appointmentDao The interface for accessing Appointment entities.
     */
    @Autowired
    public SymptomDaoImpl(SymptomRepository symptomRepository, @Qualifier("userDaoImpl") UserInterface userDao, @Qualifier("departmentDaoImpl") DepartmentInterface departmentDao, @Qualifier("appointmentDaoImpl") AppointmentInterface appointmentDao) {
        this.symptomRepository = symptomRepository;
        this.userDao = userDao;
        this.departmentDao = departmentDao;
        this.appointmentDao = appointmentDao;
    }

    /**
     * Retrieves a symptom by its ID.
     * @param id The ID of the symptom to retrieve.
     * @return The symptom with the given ID.
     * @throws DoesNotExistException if the symptom does not exist.
     */
    @Override
    public Symptom getSymptom(int id) throws DoesNotExistException{
        Optional<Symptom> optionalSymptom = symptomRepository.findById(id);
        if(optionalSymptom.isEmpty()){
            throw new DoesNotExistException("Symptom");
        }
        return optionalSymptom.get();
    }

    /**
     * Retrieves all symptoms.
     * @return A list of all symptoms.
     */
    @Override
    public Page<Symptom> getAllSymptom(int page, int size, String sortBy, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Symptom> symptoms;
        if(search != null && !search.isEmpty()){
            symptoms = symptomRepository.findAllAndNameContainsIgnoreCase(search, pageable);
        } else {
            symptoms = symptomRepository.findAll(pageable);
        }
        for(Symptom symptom : symptoms){
            Hibernate.initialize(symptom.getDepartments());
        }
        return symptoms;
    }

    @Override
    public List<Symptom> getAllSymptomList() {
        return symptomRepository.findAll();
    }

    /**
     * Creates a new symptom.
     * @param symptom The symptom to create.
     * @return The created symptom.
     * @throws DoesNotExistException if the symptom does not exist.
     * @throws MissingParameterException if a required parameter is missing.
     */
    @Transactional
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

    /**
     * Updates an existing symptom.
     * @param id The ID of the symptom to update.
     * @param symptom The updated symptom.
     * @return The updated symptom.
     * @throws DoesNotExistException if the symptom does not exist.
     * @throws MissingParameterException if a required parameter is missing.
     */
    @Transactional
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

    /**
     * Deletes a symptom.
     * @param id The ID of the symptom to delete.
     * @return true if the symptom was successfully deleted, false otherwise.
     * @throws DoesNotExistException if the symptom does not exist.
     */
    @Transactional
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
