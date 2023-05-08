import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { MedicalHistory } from "../models/medicalHistory.model";
import { UserService } from "./user.service";
import { User } from "../models/user.model";
import { Observable, map, switchMap } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MedicalHistoryService {

  constructor(private http : HttpClient, private userService : UserService) { }

  base_url = "http://localhost:8080/"

  getUserEmail(): Observable<string> {
    return this.userService.getUserByEmail().pipe(
      map((user: User) => {
        return user.email;
      })
    )
  }

  getMedicalHistoryByUserEmail(): Observable<MedicalHistory[]>{
    return this.getUserEmail().pipe(
      switchMap((email: string) => {
        return this.http.get<MedicalHistory[]>(`${this.base_url}postAppointmentData/email/${email}`);
      })
    );
  }

  getMedicalHistoryById(appointmentId:any){
    return this.http.get<MedicalHistory>(`${this.base_url}postAppointmentData/id/${appointmentId}`);
  }
}
