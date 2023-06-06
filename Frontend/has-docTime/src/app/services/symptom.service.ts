import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Symptom } from '../models/symptom.model';
import { DiseaseCount } from '../models/diseaseCount.model';
import { PastAppointment } from '../models/pastAppointment.model';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { PagedObject } from '../models/pagedObject.model';

@Injectable({
  providedIn: 'root'
})
export class SymptomService {

  baseUrl = environment.apiUrl

  constructor(private http : HttpClient) { }

  getSymptoms(params : any): Observable<any>{
    let httpParams = new HttpParams();
    Object.keys(params).forEach((key) => {
      httpParams = httpParams.set(key, params[key]);
    });
    return this.http.get<PagedObject>(`${this.baseUrl}symptom`, { params: httpParams });
  }

  getSymptomsList(){
    return this.http.get<Symptom[]>(`${this.baseUrl}symptom/list`);
  }

  getDiseaseListWithCaseCountFromSymptom(symptom : string) {
    symptom = symptom.toLowerCase();
    return this.http.get<DiseaseCount[]>(`${this.baseUrl}postAppointmentData/diseaseList/${symptom}`);
  }


  getPastAppointmentsFromSymptom(symptom : string | undefined, params : any): Observable<any> {
    symptom = (symptom as string).toLowerCase() ;
    let httpParams = new HttpParams();
    Object.keys(params).forEach((key) => {
      httpParams = httpParams.set(key, params[key]);
    });
    return this.http.get<PagedObject>(`${this.baseUrl}postAppointmentData/data/${symptom}`, { params: httpParams });
  }

  getSymptomById(id : number){
    return this.http.get<Symptom>(`${this.baseUrl}symptom/${id}`);
  }
}
