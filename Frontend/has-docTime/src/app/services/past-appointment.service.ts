import { Injectable, OnInit } from '@angular/core';
import { PastAppointment } from '../models/pastAppointment.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PastAppointmentService {

  constructor(private http : HttpClient) { }
  base_url = environment.apiUrl;

  getPastAppointmentDataByDoctor(id: string){
    return this.http.get<PastAppointment[]> (`${this.base_url}postAppointmentData/doctor/${id}`);
  }

  getPastAppointmentDataByUser(id: string | undefined | null){
    return this.http.get<PastAppointment[]>(`${this.base_url}postAppointmentData/user/${id}`);
  }

  getPastAppointmentDataByUserEmail(): Observable<PastAppointment[]>{
    return this.http.get<PastAppointment[]>(`${this.base_url}postAppointmentData/findByUserEmail`);
  }

  getPastAppointmentDataById(appointmentId:any){
    return this.http.get<PastAppointment>(`${this.base_url}postAppointmentData/${appointmentId}`);
  }

  getPastAppointmentData(){
    return this.http.get<PastAppointment[]>(`${this.base_url}postAppointmentData`);
  }

}
