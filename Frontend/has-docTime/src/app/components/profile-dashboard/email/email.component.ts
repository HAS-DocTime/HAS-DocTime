import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Token } from 'src/app/models/token.model';
import { AdminService } from 'src/app/services/admin.service';
import { AuthService } from 'src/app/services/auth.service';
import { DoctorService } from 'src/app/services/doctor.service';
import { ToastMessageService } from 'src/app/services/toast-message.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent implements OnInit{

  constructor(
    private authService : AuthService, 
    private userService : UserService,
    private adminService : AdminService,
    private doctorService : DoctorService,
    private toast : ToastMessageService
    ) {}

  userId! : number;
  decodedToken! : Token;
  userRole! : string;
  userEmail! : string;
  id! : number;

  ngOnInit(): void {
      this.decodedToken = this.authService.decodeToken();
      this.userRole = this.decodedToken.role;
      this.userId = parseInt(this.decodedToken.id);
      this.userEmail = this.decodedToken.sub;

      if(this.userRole === "PATIENT"){
        this.id = this.userId;
      }else if(this.userRole === "DOCTOR"){
        this.doctorService.getDoctor(this.userId).subscribe(data => {
          this.id = data.user.id!;
        });
      }else{
        this.adminService.getAdmin(this.userId).subscribe(data => {
          this.id = data.user?.id!;
        });
      }
  }

  updateEmailForm : FormGroup = new FormGroup(
    {
      email : new FormControl('', [Validators.email])
    }
  )

  updateEmail(){
    const emailObj = this.updateEmailForm.value;
    console.log(emailObj);
      emailObj['id'] = this.id;
      emailObj['role'] = this.userRole;
      console.log(emailObj);
      
      this.userService.updateEmail(emailObj).subscribe((data)=> {
        const token : string = data.token;
        sessionStorage.setItem("token", token);
        this.decodedToken  = this.authService.decodeToken();
        this.userEmail = this.decodedToken.sub;
        this.toast.showSuccess("Email Updated Successfully", "Success");
        this.updateEmailForm.controls['email'].patchValue("");
      }, (err) => {
        if(err){
          this.toast.showError("Email Already exists", "Error");
          this.updateEmailForm.controls['email'].patchValue("");
        }
      });
  }
}
