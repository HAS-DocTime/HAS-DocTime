import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Admin } from '../models/admin.model';
import { User } from '../models/user.model';
import { Doctor } from '../models/doctor.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdminService{

  constructor(private http : HttpClient) { }

  baseUrl = environment.apiUrl;

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

  getAllDoctors(){
    return this.http.get<Doctor[]>(`${this.baseUrl}doctor`);
  }

  getDoctorsByDepartmentId(id : number){
    return this.http.get<Doctor[]>(`${this.baseUrl}doctor/department/${id}`);
  }

  deleteUser(id : number){
    return this.http.delete<User>(`${this.baseUrl}user/${id}`);
  }

}
