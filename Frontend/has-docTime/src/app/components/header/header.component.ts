import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{

  constructor(private userService: UserService){
  }

  registered!: Boolean;
  inSignupForm!: Boolean;

  ngOnInit(): void {
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
