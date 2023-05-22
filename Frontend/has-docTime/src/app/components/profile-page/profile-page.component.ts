import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';
import { DatePipe } from '@angular/common';
import { Doctor } from 'src/app/models/doctor.model';
import { DoctorService } from 'src/app/services/doctor.service';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent implements OnInit, OnDestroy {

  constructor(private datePipe: DatePipe, private userService: UserService, private cdr: ChangeDetectorRef,
    private doctorService: DoctorService) {

  }

  user?: User;
  doctor?: Doctor;
  disable: boolean = true;
  firstName: string = "";
  lastName: string = "";
  id: number = 0;
  tokenRole: string = "";
  dateFromAPI = new Date('2022-05-12T00:00:00');
  formattedDate = this.datePipe.transform(this.dateFromAPI, 'yyyy-MM-dd');

  ngOnInit(): void {

    const token = sessionStorage.getItem('token');
    if (token) {

      // console.log();

      let store = token?.split('.');
      this.tokenRole = atob(store[1]).split(',')[2].split(':')[1];
      console.log(atob(store[1]));
      this.id = parseInt(atob(store[1]).split(',')[1].split(':')[1].substring(1, this.tokenRole.length - 1));

      this.tokenRole = this.tokenRole.substring(1, this.tokenRole.length - 1);
      console.log(this.tokenRole);
      if (this.tokenRole === "DOCTOR") {
        // console.log("doctor called");

        this.doctorService.getDoctor(this.id).subscribe(data => {


          this.doctor = data;
          this.user = data.user;
          this.id = data.id as number;
          const docNameArray = this.doctor?.user.name.split(" ", 2);
          this.firstName = docNameArray[0];
          this.lastName = docNameArray[1];
          this.dateFromAPI = new Date(data.user.dob);
          this.formattedDate = this.datePipe.transform(this.dateFromAPI, 'yyyy-MM-dd');

          this.editForm.patchValue({
            firstName: this.firstName,
            lastName: this.lastName,
            email: this.doctor.user.email,
            gender: this.doctor.user.gender,
            bloodGroup: this.doctor.user.bloodGroup,
            contact: this.doctor.user.contact,
            height: this.doctor.user.height,
            weight: this.doctor.user.weight,
            dob: this.formattedDate,
            qualification: this.doctor.qualification,
            casesSolved: this.doctor.casesSolved
          });
        });
      }

      if (this.tokenRole === "PATIENT") {
        this.userService.getUser().subscribe(data => {
          this.user = data;
          this.id = data.id as number;
          const nameArray = this.user?.name.split(" ", 2);
          this.firstName = nameArray[0];
          this.lastName = nameArray[1];
          this.dateFromAPI = new Date(data.dob);

          this.formattedDate = this.datePipe.transform(this.dateFromAPI, 'yyyy-MM-dd');

          this.editForm.patchValue({
            firstName: this.firstName,
            lastName: this.lastName,
            email: this.user.email,
            gender: this.user.gender,
            bloodGroup: this.user.bloodGroup,
            contact: this.user.contact,
            height: this.user.height,
            weight: this.user.weight,
            dob: this.formattedDate
          });

        });
      }

    }



  }

  editForm: FormGroup = new FormGroup({
    firstName: new FormControl({ value: "", disabled: this.disable }, [Validators.required]),
    lastName: new FormControl({ value: "", disabled: this.disable }, [Validators.required]),
    dob: new FormControl({ value: "", disabled: this.disable }, [Validators.required]),
    gender: new FormControl({ value: "", disabled: this.disable }, [Validators.required]),
    bloodGroup: new FormControl({ value: "", disabled: this.disable }, [Validators.required]),
    contact: new FormControl({ value: "", disabled: this.disable }, [Validators.required]),
    height: new FormControl({ value: "", disabled: this.disable }),
    weight: new FormControl({ value: "", disabled: this.disable }),
    email: new FormControl({ value: "", disabled: this.disable }, [Validators.required]),
    qualification: new FormControl({ value: "", disabled: this.disable }),
    casesSolved: new FormControl({ value: "", disabled: true }),
    patientChronicIllness: new FormArray([])
  })



  ngOnDestroy(): void {

  }

  onSubmit() {

    const date = new Date();

    const user = this.editForm.value;

    if (date.getFullYear() > new Date(user.dob as Date).getFullYear()) {
      let age = date.getFullYear() - new Date(user.dob as Date).getFullYear() - 1;
      if (date.getMonth() > new Date(user.dob as Date).getMonth()) {
        age++;
      }
      else if (date.getMonth() === (new Date(user.dob as Date).getMonth())) {
        if (date.getDate() >= new Date(user.dob as Date).getDate()) {
          age++;
        }
      }
      user.age = age;

    }
    user.name = user.firstName + " " + user.lastName;
    let chronicIllnesses = [];
    for (let i = 0; i < user.patientChronicIllness.length; i++) {
      let chronicIllness = {
        "chronicIllness": {
          "id": parseInt(user.patientChronicIllness[i].name)
        },
        "yearsOfIllness": user.patientChronicIllness[i].yearsOfIllness
      }
      chronicIllnesses.push(chronicIllness);
    }
    user.patientChronicIllness = chronicIllnesses;
    if (this.tokenRole === "PATIENT") {
      user.role = this.user?.role;
      user.email = this.user?.email;

      this.userService.updateUser(user, this.id).subscribe((data) => {
        this.toggleDisable();
        // this.user = data;
        // location.reload();
        this.cdr.detectChanges();
        // this.user.role =


        this.userService.getUser().subscribe(data => {
          this.user = data;
          // this.editForm.valueChanges.subscribe(data => {
          //   this.user = data;
          //   console.log(this.user);
          // });
        });

      });
    }
    else if (this.tokenRole === "DOCTOR") {
      let userId = 0;
      // this.userService.registerUser(signupDetail).subscribe((data) => {
      //   this.authToken = data;
      //   sessionStorage.clear();
      //   localStorage.clear();
      //   localStorage.setItem('token', JSON.stringify(data));
      //   window.sessionStorage.setItem('token',JSON.stringify(data));
      //   signupDetail = {email : "", password : ""};
      // });
      user.role = this.doctor?.user.role;
      user.email = this.doctor?.user.email;
      let doctor: Doctor = {
        "user": user,
        "qualification": this.editForm.value.qualification,
        "casesSolved": this.editForm.value.casesSolved,
        "available": true,
        "department": {
          id: 1
        }
      };
      this.userService.updateDoctor(doctor, this.id).subscribe((data) => {
        // const authToken = data;
        // console.log(authToken.token);
        this.doctorService.getDoctor(this.id).subscribe(data => {
          this.doctor = data;
          this.user = data.user;
          this.cdr.detectChanges();
        });
        this.toggleDisable();
      });
    }





  }

  toggleDisable() {
    this.disable = !this.disable;
    // update the disable property of each form field
    Object.keys(this.editForm.controls).forEach(key => {
      if (this.disable) {
        this.editForm.controls[key].disable();
      } else {
        if (key !== "casesSolved" && key !== "email") {
          this.editForm.controls[key].enable();
        }

      }
    });
  }
}
