import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Appointment } from 'src/app/models/appointment.model';
import { AppointmentService } from 'src/app/services/appointment.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})

export class AppointmentComponent implements OnInit{

  constructor(private appointmentService : AppointmentService, private userService : UserService, private router : Router, private route : ActivatedRoute){}

  appointments : Appointment[] = [];

  ngOnInit(){

    this.userService.getUserByEmail().subscribe((data)=>{
        this.appointmentService.getAppointmentByUser((data.id.toString())).subscribe((data)=> {
          console.log(data);
          this.appointments = data;
        });
    })

  }

  appointmentDetails(id : number | undefined){
    this.router.navigate([id], {relativeTo : this.route})
  }

  deleteAppointment(id : number | undefined){
    this.appointmentService.deleteAppointment(id).subscribe((data)=> {
      this.userService.getUserByEmail().subscribe((data)=>{
        this.appointmentService.getAppointmentByUser((data.id.toString())).subscribe((data)=> {
          this.appointments = data;
        });
    })
    }
    );
  }

}
