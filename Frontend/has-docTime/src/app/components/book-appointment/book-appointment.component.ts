import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Symptom } from 'src/app/models/symptom.model';

import { AppointmentService } from 'src/app/services/appointment.service';
import { SymptomService } from 'src/app/services/symptom.service';
import { ToastMessageService } from 'src/app/services/toast-message.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-book-appointment',
  templateUrl: './book-appointment.component.html',
  styleUrls: ['./book-appointment.component.css']
})
export class BookAppointmentComponent implements OnInit{

  symptoms : Symptom[] = [];
  selectedSymptom : number[] = [];
  tokenRole! : string;
  id! : number;

  constructor(private symptomService : SymptomService, private appointmentService : AppointmentService,
     private userService : UserService, private router : Router, private route : ActivatedRoute, private location : Location, private toast: ToastMessageService){}

  ngOnInit(){
    const token = sessionStorage.getItem('token');
    if (token) {

      let store = token?.split('.');
      this.tokenRole = atob(store[1]).split(',')[2].split(':')[1];

      this.id = parseInt(atob(store[1]).split(',')[1].split(':')[1].substring(1, this.tokenRole.length - 1));

      this.tokenRole = this.tokenRole.substring(1, this.tokenRole.length - 1);
    }
    this.symptomService.getSymptomsList().subscribe((data)=> {
      this.symptoms = data;
    })

    this.bookAppointment.controls['symptoms'].valueChanges.subscribe(data=> {
      this.selectedSymptom = [];
      for(let symptomName of data){
        if(symptomName['id']!=='')
        this.selectedSymptom.push(parseInt(symptomName['id']))
      }
    })
  }

  bookAppointment : FormGroup = new FormGroup({
    symptoms : new FormArray([
      new FormGroup({
          id : new FormControl('', Validators.required)
      })
    ]),
    description : new FormControl("")
  })

  createAppointment(){
    for(let symptom of this.symptomList.value){
      symptom.id = parseInt(symptom.id);
    }
    this.bookAppointment.value["user"] = {
      "id": this.id
    }
    //Hard-Coded as of now
    this.bookAppointment.value["doctor"] = {
      "id": 2
    }
    //Hard-Coded as of now
    this.bookAppointment.value["timeSlotForAppointment"] = {
      "id": 8
    }
    console.log("-------------------",this.bookAppointment.value);

    this.appointmentService.createAppointment(this.bookAppointment.value).subscribe((data)=> {
      this.router.navigate(["../"], {relativeTo : this.route});
      this.toast.showSuccess(`Appointemnt created successfully`, "Created!");
    })
  }

  get symptomList() : FormArray{
    return this.bookAppointment.get('symptoms') as FormArray
  }

  addSymptom(){
    this.symptomList.push(new FormGroup({
      id : new FormControl('', Validators.required)
    }))
  }

  deleteSymptom(id : number){
    this.symptomList.removeAt(id);
  }

  backToAppointments(){
    this.router.navigate(["../"], {relativeTo : this.route})
  }

  navigateBack(){
    this.location.back();
  }
}
