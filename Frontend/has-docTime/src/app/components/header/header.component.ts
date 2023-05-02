import { ChangeDetectorRef, Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{

  constructor(private cdr: ChangeDetectorRef,private loginService: LoginService, private userService: UserService, private router: Router, private route: ActivatedRoute){
  }
ngAfterViewChecked(){
   //your code to update the model
   this.cdr.detectChanges();
}

  isLoggedIn!: Boolean;
  inSignupForm!: Boolean;
  inLoginForm!: Boolean;

  ngOnInit(): void {

      this.userService.isLoggedIn.subscribe( (data) => {
        this.isLoggedIn = data;
      });
      this.userService.inSignup.subscribe(value => {
        this.inSignupForm = value
      })
      this.userService.inLogin.subscribe(value => {
        this.inLoginForm = value
      })

  }

  onLogout(){
    this.userService.logOutUser();
    sessionStorage.removeItem("token");
    this.router.navigate(['/login']);
  }

}
