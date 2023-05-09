import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { MedicalHistory } from "../models/medicalHistory.model";
import { UserService } from "./user.service";
import { Observable, map, switchMap } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MedicalHistoryService {

  constructor(private http : HttpClient, private userService : UserService) { }

  base_url = "http://localhost:8080/"



  getMedicalHistoryByUserEmail(): Observable<MedicalHistory[]>{
    return this.http.get<MedicalHistory[]>(`${this.base_url}postAppointmentData/findByUserEmail`);
  }

  getMedicalHistoryById(appointmentId:any){
    return this.http.get<MedicalHistory>(`${this.base_url}postAppointmentData/${appointmentId}`);
  }
}
