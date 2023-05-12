import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { MedicalHistory } from "../models/medicalHistory.model";
import { UserService } from "./user.service";
import { Observable, catchError, map, switchMap } from "rxjs";

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

  createMedicalHistory(medicalHistory : MedicalHistory): Observable<MedicalHistory> {
    return this.http.post<MedicalHistory>(`${this.base_url}postAppointmentData`, medicalHistory);
  }

  updateMedicalHistory(medicalHistory : MedicalHistory, id: number): Observable<MedicalHistory> {
    console.log(id)
    console.log(medicalHistory)
    return this.http.put<MedicalHistory>(`${this.base_url}postAppointmentData/${id}`, medicalHistory);
  }
}
