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

  id : number = 0;
  tokenRole : string = "";

  ngOnInit(){

    const token = sessionStorage.getItem('token');
    if (token) {

      let store = token?.split('.');
      this.tokenRole = atob(store[1]).split(',')[2].split(':')[1];

      this.id = parseInt(atob(store[1]).split(',')[1].split(':')[1].substring(1, this.tokenRole.length - 1));

      this.tokenRole = this.tokenRole.substring(1, this.tokenRole.length - 1);
    }

    this.userService.getUserByEmail().subscribe((data)=>{

      if(this.tokenRole==='ADMIN'){
        this.appointmentService.getAppointments().subscribe((data)=>{
          this.appointments = data;
        })
      }
      // else if(this.tokenRole === "DOCTOR"){
      //   console.log("DOCTOR")
      //   this.appointmentService.getAppointmentByDoctor(data.id as number).subscribe((data)=> {
      //     this.appointments = data;
      //   })
      // }
      else {
        this.appointmentService.getAppointmentByUser((data.id as number)).subscribe((data)=> {
          this.appointments = data;
        });
      }

    })

  }

  appointmentDetails(id : number){
    this.router.navigate([id], {relativeTo : this.route})
  }

  deleteAppointment(id : number){
    this.appointmentService.deleteAppointment(id).subscribe((data)=> {
      this.userService.getUserByEmail().subscribe((data)=>{
        this.appointmentService.getAppointmentByUser((data.id as number)).subscribe((data)=> {
          this.appointments = data;
        });
    })
    }
    );
  }

}
