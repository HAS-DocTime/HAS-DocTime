import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Appointment } from 'src/app/models/appointment.model';
import { MedicalHistory } from 'src/app/models/medicalHistory.model';
import { AppointmentService } from 'src/app/services/appointment.service';
import { MedicalHistoryService } from 'src/app/services/medicalHistory.service';

@Component({
  selector: 'app-doctor-schedule-appointments',
  templateUrl: './doctor-schedule-appointments.component.html',
  styleUrls: ['./doctor-schedule-appointments.component.css']
})
export class DoctorScheduleAppointmentsComponent {

  constructor(private appointmentService: AppointmentService, private router : Router, private route : ActivatedRoute){}

  appointments: Appointment[]=[];
  id: string = "";

  ngOnInit() {
    const token = sessionStorage.getItem('token');
      console.log(token);
      if (token) {
        let store = token?.split('.');
        this.id = atob(store[1]).split(',')[1].split(':')[1];
        this.id = this.id.substring(1, this.id.length-1);
      }
    this.appointmentService.getAppointmentsByDoctor(this.id)
    .subscribe(
      (data) => {
        console.log(data);
        this.appointments = data;
      },
      (error: any) => {
        console.error('Error getting doctor apppointments', error);
      }
    );
  }

  getdetailedHistory(id : any){

    this.router.navigate([id], {relativeTo : this.route});
  }

}
