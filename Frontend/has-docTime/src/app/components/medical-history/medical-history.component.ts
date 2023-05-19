import { Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MedicalHistory } from 'src/app/models/medicalHistory.model';
import { Symptom } from 'src/app/models/symptom.model';
import { MedicalHistoryService } from 'src/app/services/medicalHistory.service';
import { SymptomService } from 'src/app/services/symptom.service';

@Component({
  selector: 'app-medical-history',
  templateUrl: './medical-history.component.html',
  styleUrls: ['./medical-history.component.css']
})
export class MedicalHistoryComponent implements OnInit{


  constructor(private medicalHistoryService: MedicalHistoryService, private symptomService: SymptomService, private router : Router, private route : ActivatedRoute){}

  id : number = 0;
  tokenRole : string = "";
  symptomId: number = 0;
  symptom! : Symptom;
  noDataFound : boolean = false;
  noDataFoundImg : string = "https://firebasestorage.googleapis.com/v0/b/ng-hasdoctime-images.appspot.com/o/dataNotFound.png?alt=media&token=2533f507-7433-4a70-989d-ba861273e537";

  medicalHistoryList: MedicalHistory[]=[];

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
            this.symptomService.getMedicalHistoryFromSymptom(this.symptom?.name).subscribe((data)=>{
              if(data===null){
                this.noDataFound = true;
              }
              this.medicalHistoryList = data;
          })
         })
        }
        else{
          this.medicalHistoryService.getMedicalHistory()
          .subscribe(
            (data: MedicalHistory[]) => {
              if(data===null){
                this.noDataFound = true;
              }
              this.medicalHistoryList = data;
            },
            (error: any) => {
              console.error('Error getting medical history:', error);
            }
          );
        }
      })


    }

    else{
      this.medicalHistoryService.getMedicalHistoryByUserEmail()
    .subscribe(
      (data: MedicalHistory[]) => {
        this.medicalHistoryList = data;
      },
      (error: any) => {
        console.error('Error getting medical history:', error);
      }
    );
    }
  }

  getdetailedHistory(id : number | undefined){

    this.router.navigate(["dashboard", "medicalHistory", id]);
  }

}
