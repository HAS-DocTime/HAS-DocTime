import { Injectable, OnInit } from '@angular/core';
import { MedicalHistory } from '../models/medicalHistory.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PostAppointmentService {

  constructor(private http : HttpClient) { }
  base_url = "http://localhost:8080/";
 
  getPostAppointmentDataByDoctor(id: string){
    return this.http.get<MedicalHistory[]> (`${this.base_url}postAppointmentData/doctor/${id}`);
  }

  getPostAppointmentDataByUser(id: number | undefined){
    return this.http.get<MedicalHistory[]>(`${this.base_url}postAppointmentData/user/${id}`);
  }

}
