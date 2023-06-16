import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { confirmPasswordValidator } from 'src/app/customValidators/confirmPasswordMatch.validator';
import { validatePassword } from 'src/app/customValidators/validatePassword.validator';
import { Token } from 'src/app/models/token.model';
import { AdminService } from 'src/app/services/admin.service';
import { AuthService } from 'src/app/services/auth.service';
import { DoctorService } from 'src/app/services/doctor.service';
import { ToastMessageService } from 'src/app/services/toast-message.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-security',
  templateUrl: './security.component.html',
  styleUrls: ['./security.component.css']
})
export class SecurityComponent {

  constructor(private authService : AuthService, private adminService : AdminService, private doctorService : DoctorService, private userService : UserService, private toast : ToastMessageService){}

  oldPasswordType: string = "password";
  showOldPassword: boolean = false;
  passwordType: string = "password";
  showPassword: boolean = false;
  confirmPasswordType: string = "password";
  showConfirmPassword: boolean = false;

  decodedToken : Token = this.authService.decodeToken();
  tokenRole : string = this.decodedToken.role;
  userId : number = parseInt(this.decodedToken.id);

  toggleShowOldPassword(){
    this.showOldPassword = !this.showOldPassword;
    this.oldPasswordType = this.showOldPassword ? "text" : "password";
  }

  toggleShowPassword() {
    this.showPassword = !this.showPassword;
    this.passwordType = this.showPassword ? "text" : "password";
  }

  toggleShowConfirmPassword() {
    this.showConfirmPassword = !this.showConfirmPassword;
    this.confirmPasswordType = this.showConfirmPassword ? "text" : "password";
  }

  updatePasswordForm: FormGroup = new FormGroup({
    oldPassword : new FormControl("", [Validators.required]),
    newPassword : new FormControl("", [Validators.required, validatePassword()]),
    confirmPassword : new FormControl("", [Validators.required, validatePassword()])
  }, { validators: confirmPasswordValidator() });

  updatePassword(){
    let updatePasswordObj = this.updatePasswordForm.value;
    delete updatePasswordObj["confirmPassword"];
    if(this.tokenRole === "PATIENT"){
      updatePasswordObj["id"] = this.userId;
      this.userService.updatePassword(updatePasswordObj).subscribe((data)=> {
        this.toast.showSuccess("Success", data.message);
      },
      (err)=> {
        this.toast.showError("Error", err.error.message);
      }
      )
    }
    else if(this.tokenRole === "DOCTOR"){
      this.doctorService.getDoctor(this.userId).subscribe((data)=> {
        updatePasswordObj["id"] = data.user.id;
        this.userService.updatePassword(updatePasswordObj).subscribe((data)=> {
          this.toast.showSuccess("Success", data.message);
        }, (err)=> {
          this.toast.showError("Error", err.error.message);
        })
      })
    }
    else{
      this.adminService.getAdmin(this.userId).subscribe((data)=> {
        updatePasswordObj["id"] = data.user?.id;
        this.userService.updatePassword(updatePasswordObj).subscribe((data)=> {
          this.toast.showSuccess("Success", data.message);
        }, (err)=> {
          this.toast.showError("Error", err.error.message);
        })
      })
    }
    this.updatePasswordForm.reset();
  }

}
