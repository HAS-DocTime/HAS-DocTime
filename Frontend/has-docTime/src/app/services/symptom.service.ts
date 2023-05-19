import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Symptom } from '../models/symptom.model';
import { DiseaseCount } from '../models/diseaseCount.model';
import { MedicalHistory } from '../models/medicalHistory.model';

@Injectable({
  providedIn: 'root'
})
export class SymptomService {

  baseUrl = "http://localhost:8080/"

  constructor(private http : HttpClient) { }

  getSymptoms(){
    return this.http.get<Symptom[]>(`${this.baseUrl}symptom`);
  }

  getDiseaseWithCaseCountFromSymptom(symptom : string){
    symptom = symptom.toLowerCase();
    return this.http.get<DiseaseCount[]>(`${this.baseUrl}postAppointmentData/disease/${symptom}`);
  }

  getMedicalHistoryFromSymptom(symptom : string | undefined){
    symptom = (symptom as string).toLowerCase() ;
    return this.http.get<MedicalHistory[]>(`${this.baseUrl}postAppointmentData/data/${symptom}`);
  }

  getSymptomById(id : number){
    return this.http.get<Symptom>(`${this.baseUrl}symptom/${id}`);
  }
}
