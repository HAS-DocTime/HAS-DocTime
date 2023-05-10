import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Appointment } from '../models/appointment.model';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http : HttpClient) { }

  baseUrl = "http://localhost:8080/";

  getAppointmentByUser(userId : number){
    return this.http.get<Appointment[]>(`${this.baseUrl}appointment/user/${userId}`);
  }

  getAppointment(id : number){
    return this.http.get<Appointment>(`${this.baseUrl}appointment/${id}`);
  }

  deleteAppointment(id : number){
    return this.http.delete<Appointment>(`${this.baseUrl}appointment/${id}`);
  }

  createAppointment(appointment : Appointment){
    return this.http.post<Appointment>(`${this.baseUrl}appointment`, appointment);
  }

}
