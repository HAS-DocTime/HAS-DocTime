import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Appointment } from '../models/appointment.model';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http : HttpClient) { }

  baseUrl = `http://192.1.200.177:8080/`;

  getAppointmentByUser(userId : string | undefined){
    return this.http.get<Appointment[]>(`${this.baseUrl}appointment/user/${userId}`);
  }

  getAppointment(id : number){
    return this.http.get<Appointment>(`${this.baseUrl}appointment/${id}`);
  }

  getAppointments(){
    return this.http.get<Appointment[]>(`${this.baseUrl}appointment`);
  }

  deleteAppointment(id : number | undefined){
    return this.http.delete(`${this.baseUrl}appointment/${id}`);
  }

  createAppointment(appointment : Appointment){
    return this.http.post<Appointment>(`${this.baseUrl}appointment`, appointment);
  }

  getAppointmentsByDoctor(id : string){
    return this.http.get<Appointment[]>(`${this.baseUrl}appointment/doctor/${id}`);
  }

}
