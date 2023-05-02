
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
  user: string = "";
  
  inLogin: Boolean = true;
  isLoggedIn: Boolean = false;


  constructor(private loginService: LoginService, private router: Router, private userService: UserService) {
  }

  ngOnInit(){
    this.inLogin = true;
  }
  loginForm:FormGroup = new FormGroup({
    email: new FormControl("", [Validators.required, Validators.email]),
    password: new FormControl("", [Validators.required, Validators.minLength(6)])
  })

  ngDoCheck(){
    this.userService.inSignup.next(false);
    this.userService.inLogin.next(this.inLogin);
    this.userService.isLoggedIn.next(this.isLoggedIn);
  }
  ngOnDestroy(): void {
      this.userService.inSignup.next(false);
      this.userService.inLogin.next(false);
      this.userService.isLoggedIn.next(this.isLoggedIn);
  }

  onSubmit(){
    this.submitted = true;
    if(this.loginForm.invalid) {
      return;
    }
    const email = this.loginForm.controls['email'].value;
    const password = this.loginForm.controls['password'].value;

    this.loginService.checkDetail(email, password).subscribe(data => {

      this.user = data.token;
      // console.log(data);
      this.isLoggedIn = true;

      sessionStorage.clear();
      sessionStorage.setItem('token',data.token);

      this.router.navigate(['/appointment']);
    }, (err)=> {
      if(err){
        this.isLoggedIn = false;
        this.invalidLogin=true;
      }
    })
    this.loginService.isLoggedIn.subscribe((data) => {
        this.isLoggedIn = data;
    });
    this.router.navigate([""]);
  }
}
