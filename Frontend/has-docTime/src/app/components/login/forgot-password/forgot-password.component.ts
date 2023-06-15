import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LoginService } from 'src/app/services/login.service';
import { ToastMessageService } from 'src/app/services/toast-message.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {

  emailSent : boolean = false;

  constructor(private loginService : LoginService, private toast : ToastMessageService){}

  forgotPasswordForm : FormGroup = new FormGroup(
    {
      email : new FormControl("", [Validators.required])
    },
  )

  sendMail(){
    console.log("Sending email");
    this.loginService.sendOtpMail(this.forgotPasswordForm.value).subscribe(()=> {
      this.emailSent = true;
      this.toast.showSuccess("Success", "Email Sent Successfully")
    }, (err)=> {
      this.toast.showError("Error", "Unexpected Error occured");
    });
  }
}
