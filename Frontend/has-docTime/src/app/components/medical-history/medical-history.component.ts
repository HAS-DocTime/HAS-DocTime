import { Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MedicalHistory } from 'src/app/models/medicalHistory.model';
import { MedicalHistoryService } from 'src/app/services/medicalHistory.service';

@Component({
  selector: 'app-medical-history',
  templateUrl: './medical-history.component.html',
  styleUrls: ['./medical-history.component.css']
})
export class MedicalHistoryComponent implements OnInit{


  constructor(private medicalHistoryService: MedicalHistoryService, private router : Router, private route : ActivatedRoute){}

  medicalHistoryList: MedicalHistory[]=[];

  ngOnInit() {
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

  getdetailedHistory(id : number | undefined){

    this.router.navigate([id], {relativeTo : this.route});
  }

}
