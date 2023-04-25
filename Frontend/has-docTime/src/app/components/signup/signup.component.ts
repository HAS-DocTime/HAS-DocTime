import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})


export class SignupComponent implements OnInit{

  constructor(private userService : UserService){

  }

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
    casesSolved : new FormControl(0)
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
        casesSolved : 0
      });
    });
  }
}
