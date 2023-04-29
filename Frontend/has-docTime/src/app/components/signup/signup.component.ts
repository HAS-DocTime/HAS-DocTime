import { Component, OnInit } from '@angular/core';
import { Form, FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { ChronicIllness } from 'src/app/models/chronicIllness.model';
import { Router } from '@angular/router';
import { Doctor } from 'src/app/models/doctor.model';
import { ChronicIllnessService } from 'src/app/services/chronic-illness.service';
import { DoctorService } from 'src/app/services/doctor.service';
import { UserService } from 'src/app/services/user.service';
import { LoginDetails } from 'src/app/models/login-details.model';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})


export class SignupComponent implements OnInit{

  constructor(private userService : UserService, private doctorService : DoctorService, private router: Router, private chhronicIllnessService : ChronicIllnessService){

  }
  // authToken: string = "";
  savedChronicIllnesses : ChronicIllness[] = [];
  message: string ="";

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

    // this.chhronicIllnessService.getAllChronicIllness().subscribe(data => {
    //   console.log(data);
    //   this.savedChronicIllnesses = data;
    // });
  }

  signupForm : FormGroup = new FormGroup({
    name : new FormControl("", [Validators.required]),
    dob : new FormControl("2001-01-01", [Validators.required]),
    gender : new FormControl("MALE", [Validators.required]),
    bloodGroup : new FormControl("O_POSITIVE", [Validators.required]),
    contact : new FormControl("", [Validators.required]),
    height : new FormControl(),
    weight : new FormControl(),
    email : new FormControl("", [Validators.required]),
    password : new FormControl("", [Validators.required]),
    role : new FormControl("PATIENT", [Validators.required]),
    qualification : new FormControl(""),
    casesSolved : new FormControl(0),
    patientChronicIllness : new FormArray([])
  })

  register(){
    console.log(this.signupForm.value);
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
    let chronicIllnesses = [];
    for(let i=0; i<user.patientChronicIllness.length; i++){
      let chronicIllness = {
        "chronicIllness" : {
          "name" : user.patientChronicIllness[i].name
        },
        "yearsOfIllness" : user.patientChronicIllness[i].yearsOfIllness
      }
      chronicIllnesses.push(chronicIllness);
    }
    user.patientChronicIllness = chronicIllnesses;
    if(user.role==="PATIENT"){

      this.userService.registerUser(signupDetail).subscribe((data) => {
        const authToken = data;
        console.log(authToken.token);
        sessionStorage.clear();
        localStorage.clear();
        localStorage.setItem('token', data.token);
        window.sessionStorage.setItem('token',data.token);
        signupDetail = {email : "", password : ""};
      });
      setTimeout(()=>{

        this.userService.createUser(user).subscribe((data)=> {
          this.signupForm.reset({
            name : "",
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
        });
      },2000);
    
      // this.userService.registerUser(signupDetail).subscribe((data) => {
        // this.authToken = data;
        // sessionStorage.clear();
        // localStorage.clear();
        // localStorage.setItem('token', JSON.stringify(data));
        // window.sessionStorage.setItem('token',JSON.stringify(data));
        // signupDetail = {email : "", password : ""};
      // });

      
      // this.registerUser(signupDetail).then((data) => {
      //   this.userService.createUser(user).subscribe((data)=> {
      //     this.signupForm.reset({
      //       name : "",
      //       dob : "2001-01-01",
      //       gender : "MALE",
      //       bloodGroup : "O_POSITIVE",
      //       contact : "",
      //       email : "",
      //       password : "",
      //       role : "PATIENT",
      //       qualification : "",
      //       casesSolved : 0,
      //       patientChronicIllness : []
      //     });
      //   });
      // });
    
      
    }
    else if(user.role === "DOCTOR"){
      let userId = 0;
      // this.userService.registerUser(signupDetail).subscribe((data) => {
      //   this.authToken = data;
      //   sessionStorage.clear();
      //   localStorage.clear();
      //   localStorage.setItem('token', JSON.stringify(data));
      //   window.sessionStorage.setItem('token',JSON.stringify(data));
      //   signupDetail = {email : "", password : ""};
      // });
      this.userService.createUser(user).subscribe((data)=> {
        userId = (data.id as number);
        let doctor : Doctor = {
          user : {
            id : userId
          },
          qualification : user.qualification,
          casesSolved : user.casesSolved,
          available : true,
          department : {
            id : 1
          } //Hard-coded as of now
        }
        this.doctorService.createDoctor(doctor).subscribe(data=> {
            this.signupForm.reset({
            name : "",
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
        })
      });
    }

    this.router.navigate([""]);

  }

//   registerUser(signupDetail: LoginDetails){
//     return new Promise((res,rej)=>{
//       this.userService.registerUser(signupDetail).subscribe((data) => {
//         this.authToken = data;
//         sessionStorage.clear();
//         localStorage.clear();
//         localStorage.setItem('token', JSON.stringify(data));
//         window.sessionStorage.setItem('token',JSON.stringify(data));
//         signupDetail = {email : "", password : ""};
//       });
//   });
// }

  addChronicIllness(){
    this.chronicIllness.push(new FormGroup(
      {
        name : new FormControl("", [Validators.required]),
        yearsOfIllness : new FormControl(0, [Validators.required, Validators.min(0.00273972601)]) //0.00273972601 = 1/365
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

}
