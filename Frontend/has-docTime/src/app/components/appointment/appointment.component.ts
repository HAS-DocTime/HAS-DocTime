import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { tap } from 'rxjs';
import { Appointment } from 'src/app/models/appointment.model';
import { MedicalHistory } from 'src/app/models/medicalHistory.model';
import { AppointmentService } from 'src/app/services/appointment.service';
import { MedicalHistoryService } from 'src/app/services/medicalHistory.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})

export class AppointmentComponent implements OnInit{

  constructor(private appointmentService : AppointmentService, private userService : UserService, private medicalHistoryService : MedicalHistoryService, private router : Router, private route : ActivatedRoute){}

  appointments : Appointment[] = [];
  i:number = 0;
  appointment? : Appointment;
  //currentTime : number = new Date().getTime();



  ngOnInit(){

    this.userService.getUserByEmail().subscribe((data)=>{
        this.appointmentService.getAppointmentByUser((data.id as number)).subscribe((data)=> {
          this.appointments = data;
        });
    })

  }

  appointmentDetails(id : number){
    this.appointment = this.appointments[this.i];
    //console.log("currentAppointment", this.appointment);

    const curr = new Date();
    // const currentTime = curr.getTime();
    const currentTime = new Date("2023-05-10T" + "23:30:00").getTime();
    console.log(currentTime);

    const nowDate = curr.toISOString().split('T')[0];
    const startTime = new Date(nowDate + 'T' + this.appointment.timeSlotForAppointment.startTime).getTime();
    console.log(startTime);

    if(currentTime > startTime) {

      const medicalHistory : MedicalHistory = {
        user : {
          id : this.appointment.user.id
        },
        doctor : {
          id : this.appointment.doctor.id
        },
        timeSlotForAppointmentData: {
          id : this.appointment.timeSlotForAppointment.id
        },
        // symptoms: this.appointment.symptoms,
        disease : '',
        medicine: ''
      };
      console.log(medicalHistory);


      this.medicalHistoryService.createMedicalHistory(medicalHistory).pipe(
        tap(()=> {
          console.log(medicalHistory);
        })
        ).subscribe(
        data => {
          console.log(medicalHistory);
          console.log('Medical history created:', data);

          this.appointmentService.deleteAppointment(this.appointment?.id as number).subscribe(
            data => {
              console.log('Appointment deleted:', data);
            }
          )
        },
        error => {
          console.error("error: ", error);
        }
      );


    }
    else {
      this.router.navigate([id], {relativeTo : this.route});
    }

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
