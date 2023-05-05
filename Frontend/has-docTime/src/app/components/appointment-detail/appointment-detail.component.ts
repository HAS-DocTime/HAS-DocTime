import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Appointment } from 'src/app/models/appointment.model';
import { AppointmentService } from 'src/app/services/appointment.service';

@Component({
  selector: 'app-appointment-detail',
  templateUrl: './appointment-detail.component.html',
  styleUrls: ['./appointment-detail.component.css']
})
export class AppointmentDetailComponent implements OnInit{

  constructor(private appointmentService : AppointmentService, private route : ActivatedRoute){
    this.route.params.subscribe((params : Params)=> {
      this.id = params["id"]
      this.appointmentService.getAppointment(this.id).subscribe((data)=> {
        console.log(data);
        this.appointment = data;
      })
    });
  }

  id! : number;
  appointment? : Appointment;

  ngOnInit(){


  }

}
