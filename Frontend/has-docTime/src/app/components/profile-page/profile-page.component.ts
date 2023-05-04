import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent implements OnInit, OnDestroy {

  user?: User;
  flag: boolean = false;
  constructor(private userService: UserService) {

  }

  ngOnInit(): void {
    this.userService.getUser().subscribe(data => {
      this.user = data;
    });

  }

  editForm : FormGroup = new FormGroup({
    firstName : new FormControl("", [Validators.required]),
    lastName : new FormControl("", [Validators.required]),
    dob : new FormControl("2001-01-01", [Validators.required]),
    gender : new FormControl("MALE", [Validators.required]),
    bloodGroup : new FormControl("O_POSITIVE", [Validators.required]),
    contact : new FormControl("", [Validators.required]),
    height : new FormControl(),
    weight : new FormControl(),
    email : new FormControl("", [Validators.required]),
    qualification : new FormControl(""),
    casesSolved : new FormControl(0),
    patientChronicIllness : new FormArray([])
  })


  onEditProfile(){
    this.flag = true;
  }

  onUpdateProfile(){
    this.flag = false;
  }

  ngOnDestroy(): void {
      
  }

  onSubmit(){
    
  }
}
