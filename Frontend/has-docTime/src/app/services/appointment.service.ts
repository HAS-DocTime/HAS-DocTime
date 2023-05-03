import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Appointment } from '../models/appointment.model';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http : HttpClient) { }

  baseUrl = "http://localhost:8080/";

  getAppointmentByUser(){
    return this.http.get<Appointment[]>(`${this.baseUrl}appointment/user/1`);
  }
}
