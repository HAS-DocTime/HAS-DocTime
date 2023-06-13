import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { Appointment } from 'src/app/models/appointment.model';
import { PatientAppointmentDetailComponent } from '../patient-appointment-detail.component';
import { SharedService } from 'src/app/services/shared.service';
import { AppointmentService } from 'src/app/services/appointment.service';

@Component({
  selector: 'app-appointment-detail-from-doctor',
  templateUrl: './appointment-detail-from-doctor.component.html',
  styleUrls: ['./appointment-detail-from-doctor.component.css']
})
export class AppointmentDetailFromDoctorComponent implements OnInit{

  appointment : Appointment = {
    id : 1,
    user : {
      name : 'Dummy Data'
    }
  };
  appointmentId?: string | null;

  constructor(private appointmentService: AppointmentService){}

  ngOnInit(){
    this.appointmentId = sessionStorage.getItem('appointmentId');
    this.appointmentService.getAppointment(parseInt(this.appointmentId as string)).subscribe(data => {
      this.appointment = data;
    });
    
  }

  

}
