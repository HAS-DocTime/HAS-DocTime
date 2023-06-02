import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Doctor } from 'src/app/models/doctor.model';
import { User } from 'src/app/models/user.model';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-user-doctor-list',
  templateUrl: './user-doctor-list.component.html',
  styleUrls: ['./user-doctor-list.component.css']
})


export class UserDoctorListComponent implements OnInit{
  urlPath! : string;
  users! : User[];
  doctors! : Doctor[];
  departmentId! : number;
  chronicIllnessId! : number;

  constructor(private adminService : AdminService, private router : Router, private route : ActivatedRoute){

  }

  ngOnInit(): void {
    this.route.url.subscribe((data)=> {
      this.urlPath = data[0].path;

      if(this.urlPath==="users"){
        this.adminService.getAllUsers().subscribe(data=>{
          this.users = data;
        })
      }
      else if(this.urlPath==="chronicIllness"){
        this.chronicIllnessId = parseInt(data[1].path);
        this.adminService.getPatientsByChronicIllnessId(this.chronicIllnessId).subscribe(data => {
          this.users = data;
        })
      }
      else if(this.urlPath==="doctors"){
        this.adminService.getAllDoctors().subscribe(data=> {
          console.log(data);
          this.doctors=data;
        })
      }
      else if(this.urlPath === "departments") {
        this.departmentId = parseInt(data[1].path);
        this.adminService.getDoctorsByDepartmentId(this.departmentId).subscribe(data => {
          this.doctors = data;
        })
      }
    })
  }

  getSingleUser(id : number | undefined){
    if(this.urlPath ==="users" || this.urlPath==="chronicIllness"){
      this.router.navigate(["dashboard", "users", id]);
    }
    else {
      this.router.navigate(["dashboard","doctors", id])
    }

  }

}
