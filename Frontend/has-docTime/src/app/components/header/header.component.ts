import { ChangeDetectorRef, Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Token } from 'src/app/models/token.model';
import { AdminService } from 'src/app/services/admin.service';
import { AuthService } from 'src/app/services/auth.service';
import { DoctorService } from 'src/app/services/doctor.service';
import { LoginService } from 'src/app/services/login.service';
import { ToastMessageService } from 'src/app/services/toast-message.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{

  constructor(
    private cdr: ChangeDetectorRef,
    private loginService: LoginService, 
    private userService: UserService, 
    private router: Router, 
    private route: ActivatedRoute, 
    private toast: ToastMessageService,
    private authService : AuthService,
    private doctorService : DoctorService,
    private adminService : AdminService
    ){
  }
ngAfterViewChecked(){
   //your code to update the model
   this.cdr.detectChanges();
}

  isLoggedIn!: Boolean;
  inSignupForm!: Boolean;
  inLoginForm!: Boolean;
  urlPath : string = "";
  forgotPassword : Boolean = false;
  changePassword : Boolean = false;
  imageUrl! : string;
  id: number = 0;
  tokenRole: string = '';


  ngOnInit(): void {
    const decodedToken : Token = this.authService.decodeToken();
    this.tokenRole = decodedToken.role;
    this.id = parseInt(decodedToken.id);
    if (this.tokenRole === 'ADMIN') {
        this.getAdmin(this.id);
    } else if (this.tokenRole === 'DOCTOR') {
      this.getDoctor(this.id)
    } else {
      this.getUser(this.id);
    }

    this.route.url.subscribe(data=> {
      if(data?.[1]?.path==="forgotPassword"){
        this.forgotPassword = true;
      }
      else{
        this.forgotPassword = false;
      }
      if(data?.[1]?.path==="changePassword"){
        this.changePassword = true;
      }
      else{
        this.changePassword = false;
      }

    })

      this.userService.inSignup.subscribe(value => {
      this.inSignupForm = value;
    })
    this.userService.inLogin.subscribe(value => {
      this.inLoginForm = value;
    })
    this.userService.isLoggedIn.subscribe( (data) => {
      this.isLoggedIn = data;
    });
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

  private getAdmin(id: number) {
    this.adminService.getAdmin(this.id).subscribe((data) => {
      this.imageUrl = data.user?.imageUrl as string;
    });
  }

  private getDoctor(id: number) {
    this.doctorService.getDoctor(id).subscribe((data) => {
      this.imageUrl = data.user.imageUrl as string;
      console.log(this.imageUrl);
      
    });
  }

  private getUser(id: number) {
    this.userService.getUser(id).subscribe((data) => {
      this.imageUrl = data.imageUrl as string;
    });
  }
}
