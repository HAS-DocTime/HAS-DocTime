/**
 * Interface defining operations for managing doctor entities.
 */
package com.spring.hasdoctime.interfaces;

import com.spring.hasdoctime.entity.Doctor;
import com.spring.hasdoctime.entity.FilteredDoctorBody;
import com.spring.hasdoctime.exceptionhandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionhandling.exception.MissingParameterException;
import org.springframework.data.domain.Page;

public interface DoctorInterface {

    /**
     * Creates a new doctor.
     *
     * @param doctor the Doctor object representing the new doctor
     * @return the created Doctor object
     * @throws MissingParameterException if there are missing parameters in the doctor object
     * @throws DoesNotExistException     if the doctor does not exist
     */
    public Doctor createDoctor(Doctor doctor) throws MissingParameterException, DoesNotExistException;

    /**
     * Retrieves a list of all doctors.
     *
     * @return List of Doctor objects representing all doctors
     */
    public Page<Doctor> getAllDoctors(int page, int size, String sortBy, String search);

    /**
     * Retrieves a doctor from its ID.
     *
     * @param id the ID of the doctor
     * @return Doctor object representing doctor of corresponding id 
     */
    public Doctor getDoctor(int id) throws DoesNotExistException;
    
    /**
     * Retrieves a list of doctors based on the department ID.
     *
     * @param id the ID of the department to filter doctors by
     * @return List of Doctor objects representing doctors in the specified department
     */
    public Page<Doctor> getDoctorsByDepartmentId(int id, int page, int size, String sortBy, String search);

    /**
     * Updates the doctor with the specified ID.
     *
     * @param id     the ID of the doctor to update
     * @param doctor the updated Doctor object
     * @return the updated Doctor object
     * @throws DoesNotExistException     if the doctor does not exist
     * @throws MissingParameterException if there are missing parameters in the doctor object
     */
    public Doctor updateDoctor(int id, Doctor doctor) throws DoesNotExistException, MissingParameterException;

    /**
     * Deletes the doctor with the specified ID.
     *
     * @param id the ID of the doctor to delete
     * @return the deleted Doctor object
     * @throws DoesNotExistException if the doctor does not exist
     */
    public Doctor deleteDoctor(int id) throws DoesNotExistException;

    public Page<Doctor> getDoctorsBySymptomsAndTimeSlot(FilteredDoctorBody filteredDoctorBody, int page, int size, String sortBy, String search) throws  DoesNotExistException;

}
