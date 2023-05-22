import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MedicalHistory } from 'src/app/models/medicalHistory.model';
import { MedicalHistoryService } from 'src/app/services/medicalHistory.service';

@Component({
  selector: 'app-patient-past-appointment-by-id',
  templateUrl: './patient-past-appointment-by-id.component.html',
  styleUrls: ['./patient-past-appointment-by-id.component.css']
})
export class PatientPastAppointmentByIdComponent implements OnInit{

  medicalHistory!: MedicalHistory;
  id!: string | null;

  constructor(private medicalHistoryService: MedicalHistoryService, private route: ActivatedRoute){}

  ngOnInit(): void {
      this.id = this.route.snapshot.paramMap.get('id');
      this.medicalHistoryService.getMedicalHistoryById(this.id).subscribe(data => {
        this.medicalHistory = data;
      });
  }
}
