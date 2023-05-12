import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { tap } from 'rxjs';
import { MedicalHistoryService } from 'src/app/services/medicalHistory.service';

@Component({
  selector: 'app-detailed-history',
  templateUrl: './detailed-history.component.html',
  styleUrls: ['./detailed-history.component.css']
})
export class DetailedHistoryComponent {
  constructor(private route : ActivatedRoute, private medicalHistoryService : MedicalHistoryService, private router : Router){}

  public id! : string | null;

  public medicalHistory?: any;

  tokenRole: string = "";

  disease : string = "";
  medicine : string = "";


  ngOnInit() {
    const token = sessionStorage.getItem('token');
    if(token){
      let store = token?.split('.');
      this.tokenRole = atob(store[1]).split(',')[2].split(':')[1];
      this.tokenRole = this.tokenRole.substring(1, this.tokenRole.length - 1);

      this.id = this.route.snapshot.paramMap.get('id');
      this.medicalHistoryService.getMedicalHistoryById(this.id)
      .subscribe((data)=> {
        this.medicalHistory = data;
      })

      if (this.tokenRole === "DOCTOR") {
        this.medicalHistoryService.getMedicalHistoryById(this.id)
        .subscribe(data =>{
          this.disease = data.disease;
          this.medicine = data.medicine;

          this.editForm.setValue({
            disease : this.disease,
            medicine : this.medicine
          });
        })
      }
    }

  }

  editForm: FormGroup = new FormGroup({
    disease: new FormControl( "" , [Validators.required]),
    medicine: new FormControl( "" , [Validators.required]),
  })

  updateDetails(){
    this.medicalHistory = {
      disease : this.editForm.get("disease")?.value,
      medicine : this.editForm.get("medicine")?.value,
      user : {
        id : this.medicalHistory.user.id
      },
      doctor : {
        id : this.medicalHistory.doctor.id
      },
      timeSlotForAppointmentData : {
        id : this.medicalHistory.timeSlotForAppointmentData.id
      }
    }
    this.medicalHistoryService.updateMedicalHistory(this.medicalHistory, parseInt(this.id as string)).subscribe(
      data => {
        this.disease = data.disease;
        this.medicine = data.medicine;
        this.medicalHistory = data;

      }, (err)=> {
        console.log(err);
        console.log(this.medicalHistory)
      }
    )
  }
}
