import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Subject } from 'rxjs';
import { Appointment } from 'src/app/models/appointment.model';
import { MedicalHistory } from 'src/app/models/medicalHistory.model';
import { MedicalHistoryService } from 'src/app/services/medicalHistory.service';
import { PostAppointmentService } from 'src/app/services/post-appointment.service';
import { SharedService } from 'src/app/services/shared.service';

@Component({
  selector: 'app-patient-appointment-detail',
  templateUrl: './patient-appointment-detail.component.html',
  styleUrls: ['./patient-appointment-detail.component.css']
})
export class PatientAppointmentDetailComponent implements OnInit{

  appointment?: Appointment;
  postAppointmentDataList?: MedicalHistory[];
  // appointmentData: Subject<Appointment | undefined> = new Subject<Appointment | undefined>();
  // postAppointmentDataListData: Subject<MedicalHistory[]> = new Subject<MedicalHistory[]>();

  constructor(
    private sharedService: SharedService,
    private postAppointmentService: PostAppointmentService
    ){}

  ngOnInit(): void {
    
  }

}
