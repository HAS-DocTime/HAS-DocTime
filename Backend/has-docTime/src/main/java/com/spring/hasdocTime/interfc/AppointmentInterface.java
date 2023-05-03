package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.Appointment;

import java.util.List;

public interface AppointmentInterface {

    public List<Appointment> getAllAppointments();

    public Appointment getAppointmentById(int id);

    public Appointment createAppointment(Appointment appointment);

    public Appointment updateAppointment(int id, Appointment appointment);

    public String deleteAppointment(int id);

    public List<Appointment> getAppointmentsByUser(int userId);

}
