import { Component, OnInit } from '@angular/core';
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

  appointment?: Appointment | null;
  postAppointmentDataList?: MedicalHistory[];

  constructor(
    private sharedService: SharedService,
    private postAppointmentService: PostAppointmentService
    ){}

  ngOnInit(): void {
      this.sharedService.getPatientAppointmentDetail().subscribe(data => {
        this.appointment = data;
        console.log(this.appointment);
        
      });

      this.postAppointmentService.getPostAppointmentDataByUser(this.appointment?.user.id).subscribe(
        data => {
          this.postAppointmentDataList = data;
        }
      );
  }

}
