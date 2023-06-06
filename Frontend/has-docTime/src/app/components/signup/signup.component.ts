import { Component, OnDestroy, OnInit } from '@angular/core';
import { Form, FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { ChronicIllness } from 'src/app/models/chronicIllness.model';
import { Router } from '@angular/router';
import { Doctor } from 'src/app/models/doctor.model';
import { ChronicIllnessService } from 'src/app/services/chronic-illness.service';
import { DoctorService } from 'src/app/services/doctor.service';
import { UserService } from 'src/app/services/user.service';
import { LoginDetails } from 'src/app/models/login-details.model';
import { confirmPasswordValidator } from 'src/app/customValidators/confirmPasswordMatch.validator';
import { validateDateValidator } from 'src/app/customValidators/validateDate.validator';
import { validatePassword } from 'src/app/customValidators/validatePassword.validator';
import { trimmedInputValidateSpace } from 'src/app/customValidators/trimmedInputValidateSpace.validator';
import { ToastMessageService } from 'src/app/services/toast-message.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})


export class SignupComponent implements OnInit, OnDestroy{

constructor(private userService : UserService, private doctorService : DoctorService, private router: Router, private chhronicIllnessService : ChronicIllnessService, private toast: ToastMessageService){

  }

  savedChronicIllnesses : ChronicIllness[] = [];
  selectedValue : string = "";
  selectedIllness : number[] = [];
  showPassword : boolean = false;
  showConfirmPassword : boolean = false;
  passwordType : string = "password";
  confirmPasswordType : string = "password";

  ngOnInit(){
      this.signupForm.get("role")?.valueChanges.subscribe(value => {
      if(value==="DOCTOR"){
        this.signupForm.get("qualification")?.addValidators(Validators.required);
        this.signupForm.get("casesSolved")?.addValidators(Validators.required);
      }
      else{
        this.signupForm.get("qualification")?.clearValidators();
        this.signupForm.get("casesSolved")?.clearValidators();
      }
      this.signupForm.controls['qualification'].updateValueAndValidity();
      this.signupForm.controls['casesSolved'].updateValueAndValidity();
    })

    this.chhronicIllnessService.getAllChronicIllnesses().subscribe(data => {
      this.savedChronicIllnesses = data as ChronicIllness[];
    });

    this.signupForm.controls["patientChronicIllness"].valueChanges.subscribe((data) => {
      this.selectedIllness = [];
      for(let illness of data){
        if(illness.name!==''){
          this.selectedIllness.push(parseInt(illness.name));
        }
      }
    })
  }

  ngDoCheck(){
    this.userService.inSignup.next(true);
    this.userService.inLogin.next(false);
    this.userService.isLoggedIn.next(false);
  }

  ngOnDestroy(): void {
      this.userService.inSignup.next(false)
      this.userService.inLogin.next(false)
      this.userService.isLoggedIn.next(true)
  }

  signupForm : FormGroup = new FormGroup({
    firstName : new FormControl("", [Validators.required, trimmedInputValidateSpace()]),
    lastName : new FormControl("", [Validators.required, trimmedInputValidateSpace()]),
    dob : new FormControl("2001-01-01", [Validators.required, validateDateValidator()]),
    gender : new FormControl("MALE", [Validators.required]),
    bloodGroup : new FormControl("O_POSITIVE", [Validators.required]),
    contact : new FormControl("", [Validators.required]),
    height : new FormControl(),
    weight : new FormControl(),
    email : new FormControl("", [Validators.required, Validators.email]),
    password : new FormControl("", [Validators.required, validatePassword()]),
    confirmPassword : new FormControl("", [Validators.required]),
    role : new FormControl("PATIENT", [Validators.required]),
    qualification : new FormControl(""),
    casesSolved : new FormControl(0),
    patientChronicIllness : new FormArray([])
  },
   {validators : confirmPasswordValidator()}
   )

  toggleShowPassword(){
    this.showPassword = !this.showPassword;
    this.passwordType = this.showPassword ? "text" : "password";
  }

  toggleShowConfirmPassword(){
    this.showConfirmPassword = !this.showConfirmPassword;
    this.confirmPasswordType = this.showConfirmPassword ? "text" : "password";
  }


  register(){
    this.signupForm.value["firstName"] = this.signupForm.value["firstName"].trim();
    this.signupForm.value["lastName"] = this.signupForm.value["lastName"].trim();
    const date = new Date();

    const email = this.signupForm.value.email;
    const password = this.signupForm.value.password;
    let signupDetail: LoginDetails = {"email" : email, "password" : password};

    const user = this.signupForm.value;
    if(date.getFullYear() > new Date(user.dob as Date).getFullYear()){
      let age = date.getFullYear() - new Date(user.dob as Date).getFullYear() -  1;
      if(date.getMonth() > new Date(user.dob as Date).getMonth()){
        age++;
      }
      else if(date.getMonth() === (new Date(user.dob as Date).getMonth())){
        if(date.getDate() >= new Date(user.dob as Date).getDate()){
          age++;

        }
      }
      user.age = age;
    }
    user.name = user.firstName + " " + user.lastName;
    let chronicIllnesses = [];
    for(let i=0; i<user.patientChronicIllness.length; i++){
      let chronicIllness = {
        "chronicIllness" : {
          "id" : parseInt(user.patientChronicIllness[i].name)
        },
        "yearsOfIllness" : user.patientChronicIllness[i].yearsOfIllness
      }
      chronicIllnesses.push(chronicIllness);
    }
    user.patientChronicIllness = chronicIllnesses;

    let doctor : Doctor = {
      "user" : user,
      "qualification" : this.signupForm.value.qualification,
      "casesSolved" : this.signupForm.value.casesSolved,
      "available" : true,
      "department" : {
        id : 1
      }
    };

    doctor.qualification = this.signupForm.value.qualification;
    if(user.role==="PATIENT"){
      this.userService.registerUser(user).subscribe((data)=> {
        sessionStorage.clear();
        sessionStorage.setItem('token', data.token);
        this.signupForm.reset({
          firstName : "",
          lastName : "",
          dob : "2001-01-01",
          gender : "MALE",
          bloodGroup : "O_POSITIVE",
          contact : "",
          email : "",
          password : "",
          role : "PATIENT",
          qualification : "",
          casesSolved : 0,
          patientChronicIllness : []
        });
        this.router.navigate(["/dashboard/appointment"]);
      }, (err)=> {
        console.log(err);
      });
    }
    else if(user.role === "DOCTOR"){
      let userId = 0;
      this.userService.registerDoctor(doctor).subscribe((data)=> {
        sessionStorage.clear();
        localStorage.clear();
        localStorage.setItem('token', data.token);
        window.sessionStorage.setItem('token',data.token);
            this.signupForm.reset({
            firstName : "",
            lastName : "",
            dob : "2001-01-01",
            gender : "MALE",
            bloodGroup : "O_POSITIVE",
            contact : "",
            email : "",
            password : "",
            role : "PATIENT",
            qualification : "",
            casesSolved : 0,
            patientChronicIllness : []
        });
        this.router.navigate(["/dashboard", "doctorScheduleAppointments"]);
        this.toast.showSuccess("Registered Successfully!", "Success");
      }, (err)=> {
        console.log(err);
        this.toast.showError("Registration Unsuccessful","Failed");
      });
    }

  }

  addChronicIllness(){
    this.chronicIllness.push(new FormGroup(
      {
        name : new FormControl("", [Validators.required]),
        yearsOfIllness : new FormControl("", [Validators.required, Validators.min(0.00273972601)]) //0.00273972601 = 1/365
      }
    ))
  }

  deleteChronicIllness(index : number){
    this.chronicIllness.removeAt(index);
  }

  get chronicIllness() : FormArray{
    return this.signupForm.get('patientChronicIllness') as FormArray
  }

  onCancel(){
    this.userService.onCancel();
  }

  get maxDate(): string {
    return new Date().toISOString().split('T')[0];
  }
  get minDate(): string {
    const date = new Date();
    date.setFullYear(date.getFullYear()-150);
    return date.toISOString().split('T')[0];
  }

}
