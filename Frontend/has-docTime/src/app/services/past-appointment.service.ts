import { Injectable, OnInit } from '@angular/core';
import { PastAppointment } from '../models/pastAppointment.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PagedObject } from '../models/pagedObject.model';

@Injectable({
  providedIn: 'root'
})
export class PastAppointmentService {

  constructor(private http : HttpClient) { }
  base_url = environment.apiUrl;

  getPastAppointmentDataByDoctor(id: string, params : any): Observable<any>{
    let httpParams = new HttpParams();
    Object.keys(params).forEach((key) => {
      httpParams = httpParams.set(key, params[key]);
    });
    return this.http.get<PagedObject> (`${this.base_url}postAppointmentData/doctor/${id}`, { params: httpParams });
  }

  getPastAppointmentDataByUser(id: string | undefined | null, params : any): Observable<any>{
    let httpParams = new HttpParams();
    Object.keys(params).forEach((key) => {
      httpParams = httpParams.set(key, params[key]);
    });
    return this.http.get<PagedObject>(`${this.base_url}postAppointmentData/user/${id}`, { params: httpParams });
  }

  getPastAppointmentDataByUserEmail(params : any): Observable<any>{
    let httpParams = new HttpParams();
    Object.keys(params).forEach((key) => {
      httpParams = httpParams.set(key, params[key]);
    });
    return this.http.get<PagedObject>(`${this.base_url}postAppointmentData/findByUserEmail`, { params: httpParams });
  }

  getPastAppointmentDataById(appointmentId:any){
    return this.http.get<PastAppointment>(`${this.base_url}postAppointmentData/${appointmentId}`);
  }

  getPastAppointmentData(params : any): Observable<any>{
    let httpParams = new HttpParams();
    Object.keys(params).forEach((key) => {
      httpParams = httpParams.set(key, params[key]);
    });
    return this.http.get<PagedObject>(`${this.base_url}postAppointmentData`, { params: httpParams });
  }

}
