import { Location } from '@angular/common';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-patient-appointment-detail',
  templateUrl: './patient-appointment-detail.component.html',
  styleUrls: ['./patient-appointment-detail.component.css']
})
export class PatientAppointmentDetailComponent implements OnInit{


  constructor(
      private location : Location
    ){}

  ngOnInit(): void {

  }

  navigateBack(){
    this.location.back();
  }

}
