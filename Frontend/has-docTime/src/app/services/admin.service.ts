import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Admin } from '../models/admin.model';
import { User } from '../models/user.model';
import { Doctor } from '../models/doctor.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService{

  constructor(private http : HttpClient) { }

  baseUrl = "http://localhost:8080/";

  getAdmin(id : number){
    return this.http.get<Admin>(`${this.baseUrl}admin/${id}`);
  }

  updateAdmin( admin: Admin, id : number ){
    return this.http.put<Admin>(`${this.baseUrl}admin/${id}`, admin);
  }

  getAllUsers(){
    return this.http.get<User[]>(`${this.baseUrl}user/patient`);
  }

  getPatientsByChronicIllnessId(id : number){
    return this.http.get<User[]>(`${this.baseUrl}user/patient/chronicIllness/${id}`);
  }

  getSingleUser(id : number){
    return this.http.get<User>(`${this.baseUrl}user/${id}`);
  }

  getAllDoctors(){
    return this.http.get<Doctor[]>(`${this.baseUrl}doctor`);
  }

  getDoctorsByDepartmentId(id : number){
    return this.http.get<Doctor[]>(`${this.baseUrl}doctor/department/${id}`);
  }

  getSingleDoctor(id : number){
    return this.http.get<Doctor>(`${this.baseUrl}doctor/${id}`);
  }

  deleteUser(id : number){
    return this.http.delete<User>(`${this.baseUrl}user/${id}`);
  }

  deleteDoctor(id : number){
    return this.http.delete<Doctor>(`${this.baseUrl}doctor/${id}`);
  }
}
