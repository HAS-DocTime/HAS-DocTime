import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Doctor } from '../models/doctor.model';
import { FilteredDoctorBody } from '../models/filteredDoctorBody.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  constructor(private http : HttpClient) { }

  baseUrl = environment.apiUrl;

  getDoctor(id : number){
    return this.http.get<Doctor>(`${this.baseUrl}doctor/${id}`);
  }

  updateDoctor(doctor : Doctor, id : number){
    return this.http.put<Doctor>(`${this.baseUrl}doctor/${id}`, doctor);
  }

  getDoctorsBySymptomAndTimeSlot(filteredDoctorBody : FilteredDoctorBody){
    return this.http.post<Doctor[]>(`${this.baseUrl}doctor/bookAppointment`, filteredDoctorBody);
  }
}
