import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Appointment } from 'src/app/models/appointment.model';
import { Department } from 'src/app/models/department.model';
import { Doctor } from 'src/app/models/doctor.model';
import { AppointmentService } from 'src/app/services/appointment.service';
import { DepartmentService } from 'src/app/services/department.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})

export class AppointmentComponent implements OnInit{

  constructor(private appointmentService : AppointmentService, private userService : UserService, private router : Router, private route : ActivatedRoute, private departmentService : DepartmentService){}

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
          for(let appointment of data){
            if(!appointment?.doctor?.department?.id){
              this.departmentService.getDepartmentById(appointment.doctor?.department as number).subscribe((data)=> {
                (appointment.doctor as Doctor).department = data;
              });
            }
          }
          this.appointments = data;
        })
      }
      else {
        this.appointmentService.getAppointmentByUser((data.id?.toString())).subscribe((data)=> {
          for(let appointment of data){
            if(!appointment?.doctor?.department?.id){
              this.departmentService.getDepartmentById(appointment.doctor?.department as number).subscribe((data)=> {
                (appointment.doctor as Doctor).department = data;
              });
            }
          }
          this.appointments = data;
        });
      }

    })

  }

  appointmentDetails(id : number | undefined){
    this.router.navigate([id], {relativeTo : this.route})
  }

  deleteAppointment(id : number | undefined){
    this.appointmentService.deleteAppointment(id).subscribe((data)=> {
      this.userService.getUserByEmail().subscribe((data)=>{
        if(this.tokenRole==="ADMIN"){
          this.appointmentService.getAppointments().subscribe((data)=> {
            for(let appointment of data){
              if(!appointment?.doctor?.department?.id){
                this.departmentService.getDepartmentById(appointment.doctor?.department as number).subscribe((data)=> {
                  (appointment.doctor as Doctor).department = data;
                });
              }
            }
            this.appointments = data;
          });
        }
        else{
          this.appointmentService.getAppointmentByUser((data.id?.toString())).subscribe((data)=> {
            for(let appointment of data){
              if(!appointment?.doctor?.department?.id){
                this.departmentService.getDepartmentById(appointment.doctor?.department as number).subscribe((data)=> {
                  (appointment.doctor as Doctor).department = data;
                });
              }
            }
            this.appointments = data;
          });
        }
    })
    }
    );
  }

}
