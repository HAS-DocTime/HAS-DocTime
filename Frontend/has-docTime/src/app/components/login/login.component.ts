
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
  tokenRole: string = "";

  inLogin: Boolean = true;
  isLoggedIn: Boolean = false;

  showPassword : boolean = false;
  passwordType : string = "password";


  constructor(private loginService: LoginService, private router: Router, private userService: UserService) {
  }

  ngOnInit(){
    this.inLogin = true;
  }
  emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  loginForm:FormGroup = new FormGroup({
    email: new FormControl("", [Validators.required, Validators.pattern(this.emailPattern)]),
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

  toggleShowPassword(){
    this.showPassword = !this.showPassword;
    this.passwordType = this.showPassword ? "text" : "password";
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
      this.isLoggedIn = true;

      sessionStorage.clear();
      sessionStorage.setItem('token',data.token);

      const token = sessionStorage.getItem('token');
      if (token) {
        let store = token?.split('.');
        this.tokenRole = atob(store[1]).split(',')[2].split(':')[1];
        this.tokenRole = this.tokenRole.substring(1, this.tokenRole.length-1);
      }
      if(this.tokenRole === "PATIENT"){
        this.router.navigate(['/dashboard/appointment']);
      }else{
        this.router.navigate(['/dashboard/doctorScheduleAppointments']);
      }
    
    }, (err)=> {
      if(err){
        this.isLoggedIn = false;
        this.invalidLogin=true;
      }
    })
    this.loginService.isLoggedIn.subscribe((data) => {
        this.isLoggedIn = data;
    });
  }
}
