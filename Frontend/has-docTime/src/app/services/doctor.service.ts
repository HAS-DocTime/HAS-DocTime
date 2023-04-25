import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Doctor } from '../models/doctor.model';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  constructor(private http : HttpClient) { }

  baseUrl = "http://localhost:8080/";

  createDoctor(doctor : Doctor){
    return this.http.post<Doctor>(`${this.baseUrl}doctor`, doctor);
  }
}
