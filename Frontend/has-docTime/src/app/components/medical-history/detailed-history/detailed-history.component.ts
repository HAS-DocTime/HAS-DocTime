import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MedicalHistory } from 'src/app/models/medicalHistory.model';
import { MedicalHistoryService } from 'src/app/services/medicalHistory.service';

@Component({
  selector: 'app-detailed-history',
  templateUrl: './detailed-history.component.html',
  styleUrls: ['./detailed-history.component.css']
})
export class DetailedHistoryComponent {
  constructor(private route : ActivatedRoute, private medicalHistoryService : MedicalHistoryService){}

  public id! : string | null;

  public medicalHistory?: any;
  symptoms: string[] = [];

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
    this.medicalHistoryService.getMedicalHistoryById(this.id)
    .subscribe((data)=> {
      this.medicalHistory = data;
      this.symptoms = data.symptoms.split("; ");
    })
  }
}
