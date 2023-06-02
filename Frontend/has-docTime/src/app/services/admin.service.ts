import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Admin } from '../models/admin.model';
import { User } from '../models/user.model';
import { Doctor } from '../models/doctor.model';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { PagedObject } from '../models/pagedObject.model';

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



  getSingleUser(id : number){
    return this.http.get<User>(`${this.baseUrl}user/${id}`);
  }

  getAllUsers(params : any): Observable<any> {
    let httpParams = new HttpParams();
    Object.keys(params).forEach((key) => {
      httpParams = httpParams.set(key, params[key]);
    });
    return this.http.get<PagedObject>(`${this.baseUrl}user/patient`, { params: httpParams });
  }

  getPatientsByChronicIllnessId(id : number, params : any): Observable<any> {
    let httpParams = new HttpParams();
    Object.keys(params).forEach((key) => {
      httpParams = httpParams.set(key, params[key]);
    });
    return this.http.get<PagedObject>(`${this.baseUrl}user/patient/chronicIllness/${id}`, { params: httpParams });
  }

  getAllDoctors( params : any): Observable<any> {
    let httpParams = new HttpParams();
    Object.keys(params).forEach((key) => {
      httpParams = httpParams.set(key, params[key]);
    });
    return this.http.get<PagedObject>(`${this.baseUrl}doctor`, { params: httpParams });
  }

  getDoctorsByDepartmentId(id : number, params : any): Observable<any> {
    let httpParams = new HttpParams();
    Object.keys(params).forEach((key) => {
      httpParams = httpParams.set(key, params[key]);
    });
    return this.http.get<PagedObject>(`${this.baseUrl}doctor/department/${id}`, { params: httpParams });
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
