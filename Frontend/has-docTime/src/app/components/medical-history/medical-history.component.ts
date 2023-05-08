import { Component, OnInit} from '@angular/core';
import { MedicalHistory } from 'src/app/models/medicalHistory.model';
import { MedicalHistoryService } from 'src/app/services/medicalHistory.service';

@Component({
  selector: 'app-medical-history',
  templateUrl: './medical-history.component.html',
  styleUrls: ['./medical-history.component.css']
})
export class MedicalHistoryComponent implements OnInit{


  constructor(private medicalHistoryService: MedicalHistoryService){}

  medicalHistoryList: any[]=[];

  ngOnInit() {
    this.medicalHistoryService.getMedicalHistoryByUserEmail()
    .subscribe(
      (data: MedicalHistory[]) => {
        for (let i = 0; i < data.length; ++i) {
          this.medicalHistoryList.push(data[i]);
        }
      },
      (error: any) => {
        console.error('Error getting medical history:', error);
      }
    );
  }

}
