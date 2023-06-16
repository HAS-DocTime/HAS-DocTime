import { ChangeDetectorRef, Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { ToastMessageService } from 'src/app/services/toast-message.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{

  constructor(private cdr: ChangeDetectorRef,private loginService: LoginService, private userService: UserService, private router: Router, private route: ActivatedRoute, private toast: ToastMessageService){
  }
ngAfterViewChecked(){
   //your code to update the model
   this.cdr.detectChanges();
}

  isLoggedIn!: Boolean;
  inSignupForm!: Boolean;
  inLoginForm!: Boolean;

  ngOnInit(): void {

    this.userService.inSignup.subscribe(value => {
      this.inSignupForm = value;
    })
    this.userService.inLogin.subscribe(value => {
      this.inLoginForm = value;
    })
    this.userService.isLoggedIn.subscribe( (data) => {
      this.isLoggedIn = data;
    });
    console.log("isLoggedIn: ", this.isLoggedIn);
    console.log("inSignForm: ", this.inSignupForm);
    console.log("inLoginFOrm: ", this.inLoginForm);
  }

  onLogout(){
    if (window.confirm('Are you sure you want to log out?')) {
      this.userService.logOutUser();
      sessionStorage.removeItem("token");
      this.router.navigate(['']);
      this.toast.showInfo("You have been Logged Out", "Logged Out");
    }else{
      this.router.navigate([this.router.url]);
    }
  }
}
