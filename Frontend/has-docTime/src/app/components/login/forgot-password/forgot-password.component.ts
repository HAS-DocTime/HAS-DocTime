import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { ToastMessageService } from 'src/app/services/toast-message.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit{

  ngOnInit(){
    document.addEventListener("DOMContentLoaded", function () {
      const otpForm = document.querySelector(".otp-form");
  const firstInput = otpForm?.querySelector("input:not([type='hidden'])");
  if (firstInput) {
    (firstInput as HTMLInputElement).focus();
  }
      let otpFields = document.querySelectorAll(".otp-form .otp-field");
      let otpValueField = document.querySelector(".otp-form .otp-value");
    
      otpFields.forEach(function (field) {
        field.addEventListener("input", function (this: HTMLInputElement) {
          this.value = this.value.replace(/[^0-9]/g, "");
          let otpValue = "";
          otpFields.forEach(function (otpField) {
            let fieldValue = (otpField as HTMLInputElement).value;
            if (fieldValue !== "") otpValue += fieldValue;
          });
          (otpValueField as HTMLInputElement).value = otpValue;
        });
    
        field.addEventListener("keyup", function (this : HTMLInputElement, e) {
          let key = (e as KeyboardEvent).key || String.fromCharCode((e as KeyboardEvent).which || (e as KeyboardEvent).keyCode);
          if (
            key === "Backspace" ||
            key === "Delete" ||
            key === "ArrowLeft" ||
            key === "ArrowRight"
          ) {
            // Backspace or Delete or Left Arrow or Down Arrow
            (this.previousElementSibling as HTMLInputElement).focus();
          } else if (
            key === "ArrowRight" ||
            key === "ArrowUp" ||
            this.value !== ""
          ) {
            // Right Arrow or Top Arrow or Value not empty
            (this.nextElementSibling as HTMLInputElement).focus();
          }
        });
    
        field.addEventListener("paste", function (e) {
          let pasteData = (e as ClipboardEvent).clipboardData?.getData("text");
          let pasteDataSplitted = pasteData?.split("");
          pasteDataSplitted?.forEach(function (value, index) {
            (otpFields[index] as HTMLInputElement).value = value;
          });
        });
      });
    });
    
  }

  emailSent : boolean = false;

  constructor(private loginService : LoginService, private toast : ToastMessageService, private router : Router){}

  forgotPasswordForm : FormGroup = new FormGroup(
    {
      email : new FormControl("", [Validators.required])
    },
  )

  sendMail(){
    console.log("Sending email");
    this.loginService.sendOtpMail(this.forgotPasswordForm.value).subscribe(()=> {
      sessionStorage.setItem("email", this.forgotPasswordForm.value["email"]);
      const div = document.querySelector(".hidden");
      (div as HTMLDivElement).style.visibility = "visible";
      this.emailSent = true;
      this.toast.showSuccess("Success", "Email Sent Successfully");
    }, (err)=> {
      this.toast.showError("Error", "Unexpected Error occured");
    });
  }

  verifyOtp(){
    let verifyOtpBody = {
      email : sessionStorage.getItem("email") as string,
      otp : (document.querySelector(".otp-form .otp-value") as HTMLInputElement).value
    }

    this.loginService.verifyOtp(verifyOtpBody).subscribe(()=> {
      this.toast.showSuccess("Success", "Verification Successful");
      this.router.navigate(["/login", "changePassword"]);
      
    }, (err)=> {
      this.toast.showError("Error", "Unexpected Error occured");
    });

  }
  
}
