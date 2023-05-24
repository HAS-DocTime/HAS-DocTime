import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Doctor } from '../models/doctor.model';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  constructor(private http : HttpClient) { }

  baseUrl = `http://192.1.200.29:8080/`;

  createDoctor(doctor : Doctor){
    return this.http.post<Doctor>(`${this.baseUrl}doctor`, doctor);
  }

  getDoctor(id : number){
    return this.http.get<Doctor>(`${this.baseUrl}doctor/${id}`);
  }

  updateDoctor(doctor : Doctor, id : number){
    return this.http.put<Doctor>(`${this.baseUrl}doctor/${id}`, doctor);
  }
}
