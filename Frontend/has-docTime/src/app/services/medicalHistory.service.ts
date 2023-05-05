import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { MedicalHistory } from "../models/medicalHistory.model";

@Injectable({
  providedIn: 'root'
})
export class MedicalHistoryService {

  constructor(private http : HttpClient) { }

  base_url = "http://localhost:8080/"

  getMedicalHistory(){
    return this.http.get<MedicalHistory[]>(`${this.base_url}postAppointmentData/email/dhruvsindha1105@gmail.com`);
  }

  getMedicalHistoryById(id:any){
    return this.http.get<MedicalHistory>(`${this.base_url}postAppointmentData/id/${id}`);
  }
}
