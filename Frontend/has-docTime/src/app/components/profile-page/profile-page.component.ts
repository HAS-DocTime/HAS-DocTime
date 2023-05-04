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
  disable: boolean = true;
  firstName : string = "";
  lastName : string = "";
  constructor(private userService: UserService) {

  }

  ngOnInit(): void {
    this.userService.getUser().subscribe(data => {
      this.user = data;
      console.log(this.user?.dob);
      const nameArray = this.user?.name.split(" ", 2);
      this.firstName = nameArray[0];
      this.lastName = nameArray[1];
      console.log(this.user);
    });

  }

  editForm : FormGroup = new FormGroup({
    firstName : new FormControl({value : "", disabled : this.disable}, [Validators.required]),
    lastName : new FormControl({value : "", disabled : this.disable} , [Validators.required]),
    dob : new FormControl({value : this.user?.dob.getDate, disabled : this.disable}, [Validators.required]),
    gender : new FormControl({value : "MALE", disabled : this.disable}, [Validators.required]),
    bloodGroup : new FormControl({value: "O_POSITIVE", disabled : this.disable}, [Validators.required]),
    contact : new FormControl({value: "", disabled : this.disable}, [Validators.required]),
    height : new FormControl({value : this.user?.height, disabled : this.disable}),
    weight : new FormControl({value : this.user?.height, disabled : this.disable}),
    email : new FormControl({value: "", disabled : this.disable}, [Validators.required]),
    qualification : new FormControl({value: "", disabled : this.disable}),
    casesSolved : new FormControl({value:0, disabled : this.disable}),
    patientChronicIllness : new FormArray([])
  })


  onEditProfile(){
    this.disable = false;
  }

  onUpdateProfile(){
    this.disable = true;
  }

  ngOnDestroy(): void {
      
  }

  onSubmit(){
    
  }
}
