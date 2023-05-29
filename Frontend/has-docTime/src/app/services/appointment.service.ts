import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Appointment } from '../models/appointment.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http : HttpClient) { }

  baseUrl = environment.apiUrl;

  getAppointmentByUser(userId : string | undefined){
    return this.http.get<Appointment[]>(`${this.baseUrl}appointment/user/${userId}`);
  }

  getAppointmentByDoctor(doctorId : number){
    return this.http.get<Appointment[]>(`${this.baseUrl}appointment/doctor/${doctorId}`);
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
