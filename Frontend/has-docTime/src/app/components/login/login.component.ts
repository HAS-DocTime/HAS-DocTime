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
  user = null;
  isLoggedIn : Boolean = false;

  constructor(private loginService: LoginService, private router: Router) {
  }

  ngOnInit(){}

  loginForm : FormGroup = new FormGroup({
    email: new FormControl("", [Validators.required, Validators.email]),
    password: new FormControl("", [Validators.required, Validators.minLength(6)])
  })

  onSubmit(){
    this.submitted = true;
    if(this.loginForm.invalid) {
      return;
    }
    const email = this.loginForm.controls['email'].value;
    const password = this.loginForm.controls['password'].value;

    this.loginService.checkDetail(email, password).subscribe(data => {
      // this.user=data;
      console.log(data);
      sessionStorage.clear();
      window.sessionStorage.setItem('token',JSON.stringify(data));
    }, (err)=> {
      if(err){
        this.invalidLogin=true;
      }
    })
    this.loginService.isLoggedIn.subscribe((data) => {
        this.isLoggedIn = data;
    });
    this.router.navigate([""]);
  }
}
