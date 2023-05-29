import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Symptom } from '../models/symptom.model';
import { DiseaseCount } from '../models/diseaseCount.model';
import { PastAppointment } from '../models/pastAppointment.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SymptomService {

  baseUrl = environment.apiUrl

  constructor(private http : HttpClient) { }

  getSymptoms(){
    return this.http.get<Symptom[]>(`${this.baseUrl}symptom`);
  }

  getDiseaseWithCaseCountFromSymptom(symptom : string){
    symptom = symptom.toLowerCase();
    return this.http.get<DiseaseCount[]>(`${this.baseUrl}postAppointmentData/disease/${symptom}`);
  }

  getPastAppointmentsFromSymptom(symptom : string | undefined){
    symptom = (symptom as string).toLowerCase() ;
    return this.http.get<PastAppointment[]>(`${this.baseUrl}postAppointmentData/data/${symptom}`);
  }

  getSymptomById(id : number){
    return this.http.get<Symptom>(`${this.baseUrl}symptom/${id}`);
  }
}
