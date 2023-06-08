import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { PastAppointment } from '../models/pastAppointment.model';
import { Appointment } from '../models/appointment.model';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  private resolvedCaseDetailedData = new BehaviorSubject<PastAppointment  | undefined>({});
  setresolvedCaseDetailedData(pastAppointment: PastAppointment | undefined){
    this.resolvedCaseDetailedData.next(pastAppointment);
  }

  getresolvedCaseDetailedData(){
    return this.resolvedCaseDetailedData;
  }

  constructor() { }
}
