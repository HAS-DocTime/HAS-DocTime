import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { MedicalHistory } from '../models/medicalHistory.model';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  private resolvedCaseDetailedData = new BehaviorSubject<MedicalHistory | null | undefined>(null);

  setresolvedCaseDetailedData(medicalHistory: MedicalHistory | undefined){
    this.resolvedCaseDetailedData.next(medicalHistory);
  }

  getresolvedCaseDetailedData(){
    return this.resolvedCaseDetailedData;
  }

  constructor() { }
}
