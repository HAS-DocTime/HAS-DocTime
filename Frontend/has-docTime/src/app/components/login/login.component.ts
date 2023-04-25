import { getLocaleId } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  submitted = false;
  invalidLogin = false;

  constructor(private loginService: LoginService, private router: Router) {
  }

  ngOnInit(){
    this.loginForm.get("email")?.addValidators([Validators.required, Validators.email]);
    this.loginForm.get("password")?.addValidators([Validators.required, Validators.minLength(8)]);
  }

  loginForm : FormGroup = new FormGroup({
    email: new FormControl("", [Validators.required]),
    password: new FormControl("", Validators.required)
  })

  onSubmit(){
    this.submitted = true;
    if(this.loginForm.invalid) {
      return;
    }
    const email = this.loginForm.controls['email'].value;
    const password = this.loginForm.controls['password'].value;

    this.loginService.checkDetail(email, password).subscribe(data => {
      console.log(data);
    }, (err)=> {
      if(err){
        this.invalidLogin=true;
      }
    })
  }
}
