import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Symptom } from '../models/symptom.model';

@Injectable({
  providedIn: 'root'
})
export class SymptomService {

  baseUrl = "http://localhost:8080/"

  constructor(private http : HttpClient) { }

  getSymptoms(){
    return this.http.get<Symptom[]>(`${this.baseUrl}symptom`);
  }
}
