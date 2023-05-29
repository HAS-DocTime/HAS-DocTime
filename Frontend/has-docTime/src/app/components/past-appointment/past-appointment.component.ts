import { Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PastAppointment } from 'src/app/models/pastAppointment.model';
import { Symptom } from 'src/app/models/symptom.model';
import { PastAppointmentService } from 'src/app/services/past-appointment.service';
import { SymptomService } from 'src/app/services/symptom.service';

@Component({
  selector: 'app-past-appointments',
  templateUrl: './past-appointment.component.html',
  styleUrls: ['./past-appointment.component.css']
})
export class PastAppointmentComponent implements OnInit{


  constructor(private pastAppointmentService: PastAppointmentService, private symptomService: SymptomService, private router : Router, private route : ActivatedRoute){}

  id : number = 0;
  tokenRole : string = "";
  symptomId: number = 0;
  symptom! : Symptom;
  noDataFound : boolean = false;
  noDataFoundImg : string = "https://firebasestorage.googleapis.com/v0/b/ng-hasdoctime-images.appspot.com/o/dataNotFound.png?alt=media&token=2533f507-7433-4a70-989d-ba861273e537";

  pastAppointmentList: PastAppointment[]=[];

  ngOnInit() {
    const token = sessionStorage.getItem('token');
    if (token) {

      let store = token?.split('.');
      this.tokenRole = atob(store[1]).split(',')[2].split(':')[1];

      this.id = parseInt(atob(store[1]).split(',')[1].split(':')[1].substring(1, this.tokenRole.length - 1));

      this.tokenRole = this.tokenRole.substring(1, this.tokenRole.length - 1);
    }

    if(this.tokenRole==="ADMIN"){
      this.route.url.subscribe((data)=>{
        if(data[0].path === "symptoms"){
          this.symptomId = parseInt(data[1].path);
          this.symptomService.getSymptomById(this.symptomId).subscribe((data)=> {
            this.symptom = data;
            this.symptomService.getPastAppointmentsFromSymptom(this.symptom?.name).subscribe((data)=>{
              if(data===null){
                this.noDataFound = true;
              }
              this.pastAppointmentList = data;
          })
         })
        }
        else{
          this.pastAppointmentService.getPastAppointmentData()
          .subscribe(
            (data: PastAppointment[]) => {
              if(data===null){
                this.noDataFound = true;
              }
              this.pastAppointmentList = data;
            },
            (error: any) => {
              console.error('Error getting past appointment:', error);
            }
          );
        }
      })


    }

    else{
      this.pastAppointmentService.getPastAppointmentDataByUserEmail()
    .subscribe(
      (data: PastAppointment[]) => {
        this.pastAppointmentList = data;
      },
      (error: any) => {
        console.error('Error getting past appointments history:', error);
      }
    );
    }
  }

  getdetailedHistory(id : number | undefined){

    this.router.navigate(["dashboard", "pastAppointments", id]);
  }

}
