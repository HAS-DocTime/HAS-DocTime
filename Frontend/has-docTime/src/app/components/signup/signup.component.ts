import { Component, OnInit } from '@angular/core';
import { Form, FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { ChronicIllness } from 'src/app/models/chronicIllness.model';
import { Doctor } from 'src/app/models/doctor.model';
import { ChronicIllnessService } from 'src/app/services/chronic-illness.service';
import { DoctorService } from 'src/app/services/doctor.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})


export class SignupComponent implements OnInit{

  constructor(private userService : UserService, private doctorService : DoctorService, private chhronicIllnessService : ChronicIllnessService){

  }

  savedChronicIllnesses : ChronicIllness[] = [];

  ngOnInit(){
    this.signupForm.get("role")?.valueChanges.subscribe(value => {
      if(value==="DOCTOR"){
        this.signupForm.get("qualification")?.addValidators(Validators.required);
        this.signupForm.get("casesSolved")?.addValidators(Validators.required);
      }
      else{
        this.signupForm.get("qualification")?.clearValidators();
        this.signupForm.get("casesSolved")?.clearValidators();
      }
      this.signupForm.controls['qualification'].updateValueAndValidity();
      this.signupForm.controls['casesSolved'].updateValueAndValidity();
    })

    this.chhronicIllnessService.getAllChronicIllness().subscribe(data => {
      console.log(data);
      this.savedChronicIllnesses = data;
    });
  }

  signupForm : FormGroup = new FormGroup({
    name : new FormControl("", [Validators.required]),
    dob : new FormControl("2001-01-01", [Validators.required]),
    gender : new FormControl("MALE", [Validators.required]),
    bloodGroup : new FormControl("O_POSITIVE", [Validators.required]),
    contact : new FormControl("", [Validators.required]),
    height : new FormControl(0.0),
    weight : new FormControl(0.0),
    email : new FormControl("", [Validators.required]),
    password : new FormControl("", [Validators.required]),
    role : new FormControl("PATIENT", [Validators.required]),
    qualification : new FormControl(""),
    casesSolved : new FormControl(0),
    patientChronicIllness : new FormArray([])
  })

  register(){
    const date = new Date();
    const user = this.signupForm.value;
    if(date.getFullYear() > new Date(user.dob as Date).getFullYear()){
      let age = date.getFullYear() - new Date(user.dob as Date).getFullYear() -  1;
      if(date.getMonth() > new Date(user.dob as Date).getMonth()){
        age++;
      }
      else if(date.getMonth() === (new Date(user.dob as Date).getMonth())){
        if(date.getDate() >= new Date(user.dob as Date).getDate()){
          age++;
        }
      }
      user.age = age;
    }
    let chronicIllnesses = [];
    for(let i=0; i<user.patientChronicIllness.length; i++){
      let chronicIllness = {
        "chronicIllness" : {
          "name" : user.patientChronicIllness[i].name
        },
        "yearsOfIllness" : user.patientChronicIllness[i].yearsOfIllness
      }
      chronicIllnesses.push(chronicIllness);
    }
    user.patientChronicIllness = chronicIllnesses;
    if(user.role==="PATIENT"){
      this.userService.registerUser(user).subscribe((data)=> {
        this.signupForm.reset({
          name : "",
          dob : "2001-01-01",
          gender : "MALE",
          bloodGroup : "O_POSITIVE",
          contact : "",
          height : 0,
          weight : 0,
          email : "",
          password : "",
          role : "PATIENT",
          qualification : "",
          casesSolved : 0,
          patientChronicIllness : []
        });
      });
    }
    else if(user.role === "DOCTOR"){
      let userId = 0;
      this.userService.registerUser(user).subscribe((data)=> {
        userId = (data.id as number);
        let doctor : Doctor = {
          user : {
            id : userId
          },
          qualification : user.qualification,
          casesSolved : user.casesSolved,
          available : true,
          department : {
            id : 1
          } //Hard-coded as of now
        }
        this.doctorService.createDoctor(doctor).subscribe(data=> {
            this.signupForm.reset({
            name : "",
            dob : "2001-01-01",
            gender : "MALE",
            bloodGroup : "O_POSITIVE",
            contact : "",
            height : 0,
            weight : 0,
            email : "",
            password : "",
            role : "PATIENT",
            qualification : "",
            casesSolved : 0,
            patientChronicIllness : []
        });
        })
      });
    }
  }

  addChronicIllness(){
    this.chronicIllness.push(new FormGroup(
      {
        name : new FormControl("", [Validators.required]),
        yearsOfIllness : new FormControl(0, [Validators.required, Validators.min(0.00273972601)]) //0.00273972601 = 1/365
      }
    ))
  }

  deleteChronicIllness(index : number){
    this.chronicIllness.removeAt(index);
  }

  get chronicIllness() : FormArray{
    return this.signupForm.get('patientChronicIllness') as FormArray
  }
}
