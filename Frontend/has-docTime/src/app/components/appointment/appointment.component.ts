import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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



  ngOnInit(){

    this.userService.getUserByEmail().subscribe((data)=>{
        this.appointmentService.getAppointmentByUser((data.id as number)).subscribe((data)=> {
          this.appointments = data;
        });
    })

  }

  appointmentDetails(appointment : Appointment){

    const curr = new Date();
    // const currentTime = curr.getTime();

    const currentTime = new Date("2023-05-12T" + "23:30:00").getTime();

    const nowDate = curr.toISOString().split('T')[0];
    const startTime = new Date(nowDate + 'T' + appointment.timeSlotForAppointment.startTime).getTime();

    if(currentTime > startTime) {

      const medicalHistory : MedicalHistory = {
        user : { id : appointment.user.id },
        doctor : { id : appointment.doctor.id },
        timeSlotForAppointmentData: { id : appointment.timeSlotForAppointment.id },
        disease : '',
        medicine: ''
      };
      console.log(medicalHistory);


      this.medicalHistoryService.createMedicalHistory(medicalHistory).subscribe(
        (data:any) => {
          medicalHistory.id = data.id;
          console.log(medicalHistory);
          console.log('Medical history created:', data);

          this.appointmentService.deleteAppointment(appointment?.id as number).subscribe(
            data => {
              console.log('Appointment deleted:', data);
              this.router.navigate(['dashboard/medicalHistory', medicalHistory.id]);


            }
          );
        },
        error => {
          console.error("error: ", error);
        }
      );



    }
    else {
      this.router.navigate([appointment.id], {relativeTo : this.route});
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
