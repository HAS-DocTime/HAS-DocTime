import { Component, OnInit } from '@angular/core';
import { AppointmentService } from 'src/app/services/appointment.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})
export class AppointmentComponent implements OnInit{

  constructor(private appointmentService : AppointmentService, private userService : UserService){}

  ngOnInit(){

    this.userService.getUserByEmail().subscribe((data)=>{
        this.appointmentService.getAppointmentByUser((data.id as number)).subscribe((data)=> {
          console.log(data);
        });
    })

  }

}
