import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PastAppointmentService } from 'src/app/services/past-appointment.service';

@Component({
  selector: 'app-detailed-history',
  templateUrl: './detailed-past-appointment.component.html',
  styleUrls: ['./detailed-past-appointment.component.css']
})
export class DetailedPastAppointmentComponent {
  constructor(private route : ActivatedRoute, private pastAppointmentService : PastAppointmentService, private router : Router, private location : Location){}

  public id! : string | null;

  public pastAppointment?: any;
  symptoms: string[] | undefined = [];
  tokenId: number = 0;
  tokenRole : string = "";
  disease : string | undefined = "";
  medicine : string | undefined = "";

  ngOnInit() {

    const token = sessionStorage.getItem('token');
    if (token) {

      let store = token?.split('.');
      this.tokenRole = atob(store[1]).split(',')[2].split(':')[1];

      this.tokenId = parseInt(atob(store[1]).split(',')[1].split(':')[1].substring(1, this.tokenRole.length - 1));

      this.tokenRole = this.tokenRole.substring(1, this.tokenRole.length - 1);
    }

    this.id = this.route.snapshot.paramMap.get('id');
    this.pastAppointmentService.getPastAppointmentDataById(this.id)
    .subscribe((data)=> {
      this.pastAppointment = data;
      this.symptoms = data.symptoms?.split("; ");
    })

    if (this.tokenRole === "DOCTOR") {
      this.pastAppointmentService.getPastAppointmentDataById(this.id)
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

  editForm: FormGroup = new FormGroup({
    disease: new FormControl( "" , [Validators.required]),
    medicine: new FormControl( "" , [Validators.required]),
  })

  updateDetails(){
    this.pastAppointment = {
      disease : this.editForm.get("disease")?.value,
      medicine : this.editForm.get("medicine")?.value,
      user : {
        id : this.pastAppointment.user.id
      },
      doctor : {
        id : this.pastAppointment.doctor.id
      },
      timeSlotForAppointmentData : {
        id : this.pastAppointment.timeSlotForAppointmentData.id
      }
    }
    this.pastAppointmentService.updatePastAppointment(this.pastAppointment, parseInt(this.id as string)).subscribe(
      data => {
        this.disease = data.disease;
        this.medicine = data.medicine;
        this.pastAppointment = data;

      }, (err)=> {
        console.log(err);
      }
    )
  }

  backToPastAppointments(){
    this.router.navigate(['../'], {relativeTo : this.route});
  }

  navigateBack(){
    this.location.back();
  }
}
