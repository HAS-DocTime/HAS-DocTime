
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Token } from 'src/app/models/token.model';
import { AuthService } from 'src/app/services/auth.service';
import { LoginService } from 'src/app/services/login.service';
import { ToastMessageService } from 'src/app/services/toast-message.service';
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


  constructor(private loginService: LoginService, private router: Router, private userService: UserService, private toast : ToastMessageService, private authService : AuthService) {
  }

  ngOnInit(){
    this.inLogin = true;
  }
  loginForm:FormGroup = new FormGroup({
    email: new FormControl("", [Validators.required, Validators.email]),
    password: new FormControl("", [Validators.required])
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

      const decoded_token : Token = this.authService.decodeToken();

      this.tokenRole = decoded_token.role;
      if(this.tokenRole === "PATIENT" || this.tokenRole === "ADMIN"){
        this.router.navigate(['/dashboard/appointment']);
      }else {
        this.router.navigate(['/dashboard/doctorScheduleAppointments']);
      }
      this.toast.showSuccess("LoggedIn Successfully!", "Success");

    }, (err)=> {
      if(err){
        this.isLoggedIn = false;
        this.invalidLogin=true;
        this.toast.showWarning("Incorrect Password", "Warning");
      }
    })
    this.loginService.isLoggedIn.subscribe((data) => {
        this.isLoggedIn = data;
    });
  }
}
