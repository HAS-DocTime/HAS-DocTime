package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.Appointment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.AppointmentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link AppointmentInterface} interface that provides the business logic for managing appointments.
 */
@Service
public class AppointmentServiceImpl implements AppointmentInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    private AppointmentInterface appointmentDao;

    /**
     * Constructs a new {@code AppointmentServiceImpl} with the specified {@link AppointmentInterface} implementation.
     *
     * @param appointmentDao The {@link AppointmentInterface} implementation.
     */
    @Autowired
    public AppointmentServiceImpl(@Qualifier("appointmentDaoImpl") AppointmentInterface appointmentDao){
        this.appointmentDao = appointmentDao;
    }


    @Override
    public Page<Appointment> getAllAppointments(int page, int size, String sortBy, String search) {
        LOGGER.info("getAllAppointments called with page: {}, size: {}, sortBy: {}, search: {}", page, size, sortBy, search);
        Page<Appointment> result = appointmentDao.getAllAppointments(page, size, sortBy, search);
        LOGGER.info("getAllAppointments returned: {}", result);
        return result;
    }

    /**
     * Retrieves all appointments.
     *
     * @return List of appointments.
     */
    @Override
    public List<Appointment> getAllAppointmentList() {
        LOGGER.info("getAllAppointmentList called");
        List<Appointment> result = appointmentDao.getAllAppointmentList();
        LOGGER.info("getAllAppointmentList returned: {}", result);
        return result;
    }

    /**
     * Retrieves an appointment by its ID.
     *
     * @param id The ID of the appointment.
     * @return The appointment.
     * @throws DoesNotExistException If the appointment does not exist.
     */
    @Override
    public Appointment getAppointmentById(int id) throws DoesNotExistException {
        LOGGER.info("getAppointmentById called with id: {}", id);
        try {
            Appointment result = appointmentDao.getAppointmentById(id);
            LOGGER.info("getAppointmentById with id: {} returned: {}", id, result);
            return result;
        } catch (DoesNotExistException e) {
            LOGGER.error("DoesNotExistException in getAppointmentById with id: {}", id, e);
            throw e;
        }
    }

    /**
     * Creates a new appointment.
     *
     * @param appointment The appointment to create.
     * @return The created appointment.
     * @throws MissingParameterException If a required parameter is missing in the new appointment.
     * @throws DoesNotExistException    If the appointment does not exist.
     */
    @Override
    public Appointment createAppointment(Appointment appointment) throws MissingParameterException, DoesNotExistException {
        LOGGER.info("createAppointment called with appointment: {}", appointment);
        try {
            Appointment result = appointmentDao.createAppointment(appointment);
            LOGGER.info("createAppointment returned: {}", result);
            return result;
        } catch (MissingParameterException e) {
            LOGGER.error("MissingParameterException in createAppointment with appointment: {}", appointment, e);
            throw e;
        } catch (DoesNotExistException e) {
            LOGGER.error("DoesNotExistException in createAppointment with appointment: {}", appointment, e);
            throw e;
        }
    }

    /**
     * Updates an appointment with the given ID.
     *
     * @param id          The ID of the appointment to update.
     * @param appointment The updated appointment.
     * @return The updated appointment.
     * @throws DoesNotExistException    If the appointment does not exist.
     * @throws MissingParameterException If a required parameter is missing in the updated appointment.
     */
    @Override
    public Appointment updateAppointment(int id, Appointment appointment) throws DoesNotExistException, MissingParameterException {
        LOGGER.info("updateAppointment called with id: {} and appointment: {}", id, appointment);
        try {
            Appointment result = appointmentDao.updateAppointment(id, appointment);
            LOGGER.info("updateAppointment with id: {} returned: {}", id, result);
            return result;
        } catch (DoesNotExistException e) {
            LOGGER.error("DoesNotExistException in updateAppointment with id: {}", id, e);
            throw e;
        } catch (MissingParameterException e) {
            LOGGER.error("MissingParameterException in updateAppointment with id: {}", id, e);
            throw e;
        }
    }

    /**
     * Deletes an appointment by its ID.
     *
     * @param id The ID of the appointment to delete.
     * @return The deleted appointment.
     * @throws DoesNotExistException If the appointment does not exist.
     */
    @Override
    public Appointment deleteAppointment(int id) throws DoesNotExistException{
        LOGGER.info("deleteAppointment called with id: {}", id);
        try {
            Appointment result = appointmentDao.deleteAppointment(id);
            LOGGER.info("deleteAppointment with id: {} returned: {}", id, result);
            return result;
        } catch (DoesNotExistException e) {
            LOGGER.error("DoesNotExistException in deleteAppointment with id: {}", id, e);
            throw e;
        }
    }

    /**
     * Retrieves all appointments associated with a user.
     *
     * @param userId The ID of the user.
     * @return List of appointments associated with the user.
     * @throws DoesNotExistException If the user does not exist.
     */
    @Override
    public Page<Appointment> getAppointmentsByUser(int userId, int page, int size, String sortBy, String search) throws DoesNotExistException{
        LOGGER.info("getAppointmentsByUser called with userId: {}, page: {}, size: {}, sortBy: {}, search: {}", userId, page, size, sortBy, search);
        try {
            Page<Appointment> result = appointmentDao.getAppointmentsByUser(userId, page, size, sortBy, search);
            LOGGER.info("getAppointmentsByUser with userId: {} returned: {}", userId, result);
            return result;
        } catch (DoesNotExistException e) {
            LOGGER.error("DoesNotExistException in getAppointmentsByUser with userId: {}", userId, e);
            throw e;
        }
    }

    /**
     * Retrieves all appointments associated with a doctor.
     *
     * @param id The ID of the doctor.
     * @return List of appointments associated with the doctor.
     * @throws DoesNotExistException If the doctor does not exist.
     */
    @Override
    public List<Appointment> getAppointmentListByUser(int userId) throws DoesNotExistException {
        LOGGER.info("getAppointmentListByUser called with userId: {}", userId);
        try {
            List<Appointment> result = appointmentDao.getAppointmentListByUser(userId);
            LOGGER.info("getAppointmentListByUser with userId: {} returned: {}", userId, result);
            return result;
        } catch (DoesNotExistException e) {
            LOGGER.error("DoesNotExistException in getAppointmentListByUser with userId: {}", userId, e);
            throw e;
        }
    }

    @Override
    public Page<Appointment> getAppointmentsOfDoctor(int id, int page, int size, String sortBy, String search) throws DoesNotExistException {
        LOGGER.info("getAppointmentsOfDoctor called with id: {}, page: {}, size: {}, sortBy: {}, search: {}", id, page, size, sortBy, search);
        try {
            Page<Appointment> result = appointmentDao.getAppointmentsOfDoctor(id, page, size, sortBy, search);
            LOGGER.info("getAppointmentsOfDoctor with id: {} returned: {}", id, result);
            return result;
        } catch (DoesNotExistException e) {
            LOGGER.error("DoesNotExistException in getAppointmentsOfDoctor with id: {}", id, e);
            throw e;
        }
    }
}
