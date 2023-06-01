/**
 * Interface defining operations for managing appointment entities.
 */
package com.spring.hasdocTime.interfaces;

import com.spring.hasdocTime.entity.Appointment;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

import java.util.List;

public interface AppointmentInterface {

    /**
     * Retrieves a list of all appointments.
     *
     * @return List of Appointment objects representing all appointments.
     */
    public List<Appointment> getAllAppointments();

    /**
     * Retrieves the appointment with the specified ID.
     *
     * @param id the ID of the appointment to retrieve
     * @return the Appointment object representing the appointment
     * @throws DoesNotExistException if the appointment does not exist
     */
    public Appointment getAppointmentById(int id) throws DoesNotExistException;

    /**
     * Creates a new appointment.
     *
     * @param appointment the Appointment object representing the new appointment
     * @return the created Appointment object
     * @throws MissingParameterException if there are missing parameters in the appointment object
     * @throws DoesNotExistException     if the appointment does not exist
     */
    public Appointment createAppointment(Appointment appointment) throws MissingParameterException, DoesNotExistException;

    /**
     * Updates the appointment with the specified ID.
     *
     * @param id          the ID of the appointment to update
     * @param appointment the updated Appointment object
     * @return the updated Appointment object
     * @throws DoesNotExistException     if the appointment does not exist
     * @throws MissingParameterException if there are missing parameters in the appointment object
     */
    public Appointment updateAppointment(int id, Appointment appointment) throws DoesNotExistException, MissingParameterException;

    /**
     * Deletes the appointment with the specified ID.
     *
     * @param id the ID of the appointment to delete
     * @return the deleted Appointment object
     * @throws DoesNotExistException if the appointment does not exist
     */
    public Appointment deleteAppointment(int id) throws DoesNotExistException;

    /**
     * Retrieves a list of appointments for a specific user.
     *
     * @param userId the ID of the user
     * @return List of Appointment objects representing appointments for the user
     * @throws DoesNotExistException if the user does not exist
     */
    public List<Appointment> getAppointmentsByUser(int userId) throws DoesNotExistException;

    /**
     * Retrieves a list of appointments for a specific doctor.
     *
     * @param id the ID of the doctor
     * @return List of Appointment objects representing appointments for the doctor
     * @throws DoesNotExistException if the doctor does not exist
     */
    public List<Appointment> getAppointmentsOfDoctor(int id) throws DoesNotExistException;

}
