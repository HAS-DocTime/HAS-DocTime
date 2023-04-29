
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy{

  submitted = false;
  invalidLogin = false;
  user = null;

  constructor(private loginService: LoginService, private router: Router, private userService: UserService) {
  }

  ngOnInit(){
    this.userService.inLogin.next(true)
    this.userService.inSignup.next(false)
  }
  loginForm:FormGroup = new FormGroup({
    email: new FormControl("", [Validators.required, Validators.email]),
    password: new FormControl("", [Validators.required, Validators.minLength(8)])
  })

  ngOnDestroy(): void {
      this.userService.inLogin.next(false)
  }

  onSubmit(){
    this.submitted = true;
    if(this.loginForm.invalid) {
      return;
    }
    const email = this.loginForm.controls['email'].value;
    const password = this.loginForm.controls['password'].value;

    this.loginService.checkDetail(email, password).subscribe(data => {
      this.user=data;
      this.router.navigate(['/appointment']);
    }, (err)=> {
      if(err){
        this.invalidLogin=true;
      }
    })
  }
}
