import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Appointment } from '../models/appointment.model';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { PagedObject } from '../models/pagedObject.model';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http : HttpClient) { }

  baseUrl = environment.apiUrl;
  page : number = 0;
  size : number = 10;
  search : string = '';
  sortBy : string = '';

  getAppointmentByUser(userId : string | undefined, params : any): Observable<any> {
    let httpParams = new HttpParams();
    Object.keys(params).forEach((key) => {
      httpParams = httpParams.set(key, params[key]);
    });
    return this.http.get<PagedObject>(`${this.baseUrl}appointment/user/${userId}`, { params: httpParams });
  }

  getAppointmentListByUser(userId : string | undefined){
    return this.http.get<Appointment[]>(`${this.baseUrl}appointment/userList/${userId}`);
  }

  getAppointmentsByDoctor(id : string, params : any): Observable<any>{
    let httpParams = new HttpParams();
    Object.keys(params).forEach((key) => {
      httpParams = httpParams.set(key, params[key]);
    });
    return this.http.get<PagedObject>(`${this.baseUrl}appointment/doctor/${id}`, { params: httpParams });
  }

  getAppointment(id : number){
    return this.http.get<Appointment>(`${this.baseUrl}appointment/${id}`);
  }

  getAppointments(params: any): Observable<PagedObject> {

    // Create HttpParams object from params object
    let httpParams = new HttpParams();
    Object.keys(params).forEach((key) => {
      httpParams = httpParams.set(key, params[key]);
    });

    return this.http.get<PagedObject>(`${this.baseUrl}appointment`, { params: httpParams });
  }

  getAppointmentList(){
    return this.http.get<Appointment[]>(`${this.baseUrl}appointment/list`);
  }


  deleteAppointment(id : number | undefined){
    return this.http.delete(`${this.baseUrl}appointment/${id}`);
  }

  createAppointment(appointment : Appointment){
    return this.http.post<Appointment>(`${this.baseUrl}appointment`, appointment);
  }



}
