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
        this.signupForm.get("cases_solved")?.addValidators(Validators.required);
      }
      else{
        this.signupForm.get("qualification")?.clearValidators();
        this.signupForm.get("cases_solved")?.clearValidators();
      }
      this.signupForm.controls['qualification'].updateValueAndValidity();
      this.signupForm.controls['cases_solved'].updateValueAndValidity();
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
    cases_solved : new FormControl("")
  })

  register(){
    this.userService.registerUser(this.signupForm.value);
  }
}
