import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Timestamp } from 'rxjs';
import { Doctor } from 'src/app/models/doctor.model';
import { Symptom } from 'src/app/models/symptom.model';
import { User } from 'src/app/models/user.model';
import { AppointmentService } from 'src/app/services/appointment.service';
import { DoctorService } from 'src/app/services/doctor.service';
import { SymptomService } from 'src/app/services/symptom.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-book-appointment',
  templateUrl: './book-appointment.component.html',
  styleUrls: ['./book-appointment.component.css']
})
export class BookAppointmentComponent implements OnInit{

  symptoms : Symptom[] = [];
  selectedSymptom : number[] = [];
  currentUser? : User;
  startTime? : Date;
  endTime? : Date;
  currentDate? : Date;
  startTimeInString : string = "";
  endTimeInString : string = "";
  currentMonth : string = "";
  currentDay : string = "";
  tokenRole! : string;
  id! : number;
  doctorList : Doctor[] = [];

  constructor(private symptomService : SymptomService, private appointmentService : AppointmentService,
     private userService : UserService, private router : Router, private route : ActivatedRoute, private doctorService : DoctorService, private location : Location){}



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

  getDoctorsBySymptomAndTimeSlot(){
    this.startTime = this.bookAppointment.value["timeSlot"].split("-")[0];
    this.endTime = this.bookAppointment.value["timeSlot"].split("-")[1];
    this.currentDate = new Date();
    this.currentDay = this.currentDate.getDate().toString();
    this.currentMonth = (this.currentDate.getMonth()+1).toString();
    if(parseInt(this.currentDay) < 10){
      this.currentDay = "0" + this.currentDay;
    }
    if(parseInt(this.currentMonth) < 10){
      this.currentMonth = "0" + this.currentMonth;
    }
    this.currentDate.setDate(1);
    this.startTimeInString = `${this.currentDate.getFullYear()}-${this.currentMonth}-${this.currentDay}T${this.startTime}`
    this.endTimeInString = `${this.currentDate.getFullYear()}-${this.currentMonth}-${this.currentDay}T${this.endTime}`
    console.log(this.startTimeInString);
    console.log(this.endTimeInString);
    this.bookAppointment.value["timeSlotStartTime"] = this.startTimeInString;
    this.bookAppointment.value["timeSlotEndTime"] = this.endTimeInString;
    this.doctorService.getDoctorsBySymptomAndTimeSlot(this.bookAppointment.value).subscribe((data)=> {
      console.log(data);
      this.doctorList = data;
    });
    console.log(this.bookAppointment.value);

  }

  bookAppointment : FormGroup = new FormGroup({
    symptoms : new FormArray([
      new FormGroup({
          id : new FormControl('', Validators.required),

      }),
    ]),
    timeSlot : new FormControl('', Validators.required),
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
      "id": 4
    }
    //Hard-Coded as of now
    this.bookAppointment.value["timeSlotForAppointment"] = {
      "id": 5
    }
    console.log("-------------------",this.bookAppointment.value);

    this.appointmentService.createAppointment(this.bookAppointment.value).subscribe((data)=> {
      this.router.navigate(["../"], {relativeTo : this.route});
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
