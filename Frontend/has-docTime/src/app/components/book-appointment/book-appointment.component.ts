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
  date! : Date;
  startTimeInString : string = "";
  endTimeInString : string = "";
  tokenRole! : string;
  id! : number;
  doctorList : Doctor[] = [];
  noDataFound : boolean = false;
  noDataFoundImg : string = "https://firebasestorage.googleapis.com/v0/b/ng-hasdoctime-images.appspot.com/o/dataNotFound.png?alt=media&token=2533f507-7433-4a70-989d-ba861273e537";
  currentDoctor! : Doctor;
  selectedSymptoms : string[] = [];

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
      this.selectedSymptoms = [];
      for(let symptomName of data){
        if(symptomName['id']!=='')
        this.selectedSymptom.push(parseInt(symptomName['id']));
        for(let symptom of this.symptoms){
          if(parseInt(symptomName['id'])===symptom.id){
            this.selectedSymptoms.push(symptom.name as string);
            break;
          }
        }
      }
    })
  }

  getDoctorsBySymptomAndTimeSlot(){
    console.log(this.bookAppointment.value);
    this.startTime = this.bookAppointment.value["timeSlot"].split("-")[0];
    this.endTime = this.bookAppointment.value["timeSlot"].split("-")[1];
    this.date = this.bookAppointment.value["date"];
    this.startTimeInString = `${this.date}T${this.startTime}`
    this.endTimeInString = `${this.date}T${this.endTime}`
    this.bookAppointment.value["timeSlotStartTime"] = this.startTimeInString;
    this.bookAppointment.value["timeSlotEndTime"] = this.endTimeInString;
    this.doctorService.getDoctorsBySymptomAndTimeSlot(this.bookAppointment.value).subscribe((data)=> {
      this.doctorList = data;
      if(this.doctorList.length<=0){
        this.noDataFound = true;
      }
      else{
        this.noDataFound = false;
      }
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
    description : new FormControl(""),
    date : new FormControl(new Date(), Validators.required)
  })

  createAppointment(){
    for(let symptom of this.symptomList.value){
      symptom.id = parseInt(symptom.id);
    }
    this.bookAppointment.value["user"] = {
      "id": this.id
    }
    this.bookAppointment.value["doctor"] = {
      "id": this.currentDoctor.id
    }
    //Hard-Coded as of now
    this.bookAppointment.value["timeSlotForAppointment"] = {
      "id": 5
    }
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

  selectDoctor(index : number){
    this.currentDoctor = this.doctorList[index];
  }

  convertTimeStampToDate(timestamp : number | undefined){
    return new Date(timestamp as number);
  }

  getMinDate(){
    var today = new Date();
    return today.toISOString().split('T')[0];
  }

  getMaxDate(){
    var today = new Date();
    today.setDate(today.getDate()+6);
    return today.toISOString().split('T')[0];
  }
}
