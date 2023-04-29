import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{

  constructor(private userService: UserService, private loginService: LoginService){
  }

  registered!: Boolean;
  inSignupForm!: Boolean;

  ngOnInit(): void {
      this.loginService.isLoggedIn.subscribe((data) => {
        this.registered = data;
      });

      this.userService.isLoggedIn.subscribe( (data) => {
        this.registered = data;
      });

      this.userService.inSignupForm.subscribe( (data) => {
        this.inSignupForm = data;
      });
  }

  onLogout(){
    this.userService.onLogout();
    this.inSignupForm = false;
  }

  onRegister(){
    this.inSignupForm = true;
  }

}
