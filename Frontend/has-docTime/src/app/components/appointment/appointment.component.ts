import { Component, OnInit } from '@angular/core';
import { AppointmentService } from 'src/app/services/appointment.service';

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})
export class AppointmentComponent implements OnInit{

  constructor(private appointmentService : AppointmentService){}

  ngOnInit(){
    this.appointmentService.getAppointmentByUser().subscribe((data)=> {
      console.log(data);
    })
  }

}
