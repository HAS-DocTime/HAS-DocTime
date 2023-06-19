import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { confirmPasswordValidator } from 'src/app/customValidators/confirmPasswordMatch.validator';
import { validatePassword } from 'src/app/customValidators/validatePassword.validator';
import { LoginService } from 'src/app/services/login.service';
import { ToastMessageService } from 'src/app/services/toast-message.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit{

  otpForm! : any;

  ngOnInit(){
    setTimeout(()=>{
      this.otpForm = document.querySelector(".otp-form");
      const firstInput = this.otpForm?.querySelector("input:not([type='hidden'])");
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
    }, 1000);
    
  }

  emailSent : boolean = false;
  remainingMinute : number = 1;
  remainingSecond : number = 59;
  remainingMinuteForPasswordChange : number = 4;
  remainingSecondForPasswordChange : number = 59;
  loading : boolean = false;
  interval! : any;

  constructor(private loginService : LoginService, private toast : ToastMessageService, private router : Router){}

  forgotPasswordForm : FormGroup = new FormGroup(
    {
      email : new FormControl("", [Validators.required])
    },
  )

  sendMail(){
    this.loading = true;
    this.loginService.sendOtpMail(this.forgotPasswordForm.value).subscribe(()=> {
      this.loading = false;
      sessionStorage.setItem("email", this.forgotPasswordForm.value["email"]);
      const div = document.querySelector(".hidden");
      (div as HTMLDivElement).style.visibility = "visible";
      this.emailSent = true;
      this.toast.showSuccess("Success", "Email Sent Successfully");
      this.interval = setInterval(()=> {
        if(this.remainingMinute===0 && this.remainingSecond===0){
          clearInterval(this.interval);
        }
        if(this.remainingSecond>0){
          this.remainingSecond--;
        }
        else if(this.remainingMinute>0){
          this.remainingMinute--;
          this.remainingSecond = 59;
        }
      }, 1000);
    }, (err)=> {
      this.loading = false;
      this.forgotPasswordForm.reset();
      this.toast.showError("Error", "Enter email which is registered in this app.");
    });
  }

  resendOtp(){
    clearInterval(this.interval);
    this.remainingMinute = 1;
    this.remainingSecond = 59;
    this.loading = true;
    const div = document.querySelector(".hidden");
      (div as HTMLDivElement).style.visibility = "hidden";
    this.loginService.sendOtpMail({email : sessionStorage.getItem("email") as string}).subscribe(()=> {
      this.loading = false;
      this.otpForm.reset();
      (div as HTMLDivElement).style.visibility = "visible";
      this.emailSent = true;
      this.toast.showSuccess("Success", "Email Sent Successfully");
      this.interval = setInterval(()=> {
        if(this.remainingMinute===0 && this.remainingSecond===0){
          clearInterval(this.interval);
        }
        if(this.remainingSecond>0){
          this.remainingSecond--;
        }
        else if(this.remainingMinute>0){
          this.remainingMinute--;
          this.remainingSecond = 59;
        }
      }, 1000);
    }, (err)=> {
      this.loading = false;
      this.forgotPasswordForm.reset();
      this.toast.showError("Error", "Unexpected Error occured.");
    });
  }

  verifyOtp(){
    this.loading = true;
    let verifyOtpBody = {
      email : sessionStorage.getItem("email") as string,
      otp : (document.querySelector(".otp-form .otp-value") as HTMLInputElement).value
    }

    this.loginService.verifyOtp(verifyOtpBody).subscribe(()=> {
      this.loading = false;
      sessionStorage.setItem('otp', verifyOtpBody.otp);
      this.toast.showSuccess("Success", "Verification Successful");
      this.isVerified = true;
      const div = document.querySelector(".hidden");
      (div as HTMLDivElement).style.visibility = "hidden";
      let intervalForPasswordChange = setInterval(()=> {
        if(this.remainingMinuteForPasswordChange===0 && this.remainingSecondForPasswordChange===0){
          clearInterval(intervalForPasswordChange);
          this.router.navigate(['/']);
        }
        if(this.remainingSecondForPasswordChange>0){
          this.remainingSecondForPasswordChange--;
        }
        else if(this.remainingMinuteForPasswordChange>0){
          this.remainingMinuteForPasswordChange--;
          this.remainingSecondForPasswordChange = 59;
        }
      }, 1000);
    }, (err)=> {
      this.otpForm.reset();
      this.loading = false;
      this.toast.showError("Error", "OTP either wrong or expired");
    });

  }

  updatePassword(){
    this.loading = true;
    this.changePasswordForm.value["email"] = sessionStorage.getItem("email") as string;
    this.changePasswordForm.value["otp"] = sessionStorage.getItem("otp") as string;
    this.loginService.saveNewPassword(this.changePasswordForm.value).subscribe(data => {
      this.loading = false;
      sessionStorage.removeItem("otp");
      sessionStorage.removeItem("email");
      this.toast.showSuccess("Success", "Password Updated Successfully");
      this.router.navigate(['/login']);
    }, (err) => {
      this.loading = false;
      this.toast.showError("Error", "Unexpected Error occured");
    });
  }

  changePasswordForm : FormGroup = new FormGroup({
    password : new FormControl("", [validatePassword(), Validators.required]),
    confirmPassword : new FormControl("", [Validators.required])
  }, {
    validators : confirmPasswordValidator()
  })

  showPassword : boolean = false;
  showConfirmPassword : boolean = false;
  passwordType : string = "password";
  confirmPasswordType : string = "password";
  isVerified : boolean = false;

  toggleShowPassword() {
    this.showPassword = !this.showPassword;
    this.passwordType = this.showPassword ? "text" : "password";
  }

  toggleShowConfirmPassword() {
    this.showConfirmPassword = !this.showConfirmPassword;
    this.confirmPasswordType = this.showConfirmPassword ? "text" : "password";
  }
  
}
