import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Doctor } from 'src/app/models/doctor.model';
import { User } from 'src/app/models/user.model';
import { AdminService } from 'src/app/services/admin.service';
import { PreviousPageUrlServiceService } from 'src/app/services/previous-page-url-service.service';

interface SortByOption {
  label: string;
  value: string;
}

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
  noDataFound : boolean = false;
  noDataFoundImg : string = "https://firebasestorage.googleapis.com/v0/b/ng-hasdoctime-images.appspot.com/o/dataNotFound.png?alt=media&token=2533f507-7433-4a70-989d-ba861273e537";
  page = 1;
  totalPages = 1;
  size = 5;
  sortBy = '';
  search = '';
  sizeOptions = [5, 10, 15];
  idForSearch! :string;
  placeholderString : string = "";
  range(totalPages: number): number[] {
    return Array(totalPages).fill(0).map((_, index) => index + 1);
  }

  sortByOptions: SortByOption[] = [];

  constructor(private adminService : AdminService, private router : Router, private route : ActivatedRoute, private previousPageUrlService : PreviousPageUrlServiceService){}

  ngOnInit(): void {

    this.route.url.subscribe((data)=> {
      this.urlPath = data[0].path;
      if(data[1]){
        this.idForSearch = data[1].path;
      }
      if(this.urlPath ==="chronicIllness" || this.urlPath === "users"){
        this.sortBy = "name";
        this.sortByOptions.push({ label: 'Name', value: 'name' }, { label: 'Email', value: 'email' }, { label: 'Gender', value: 'gender' })
      } else {
        this.sortBy = "user.name";
        this.sortByOptions.push({ label: 'Name', value: 'user.name' }, { label: 'Email', value: 'user.email' }, { label: 'Gender', value: 'user.gender' })
      }
      this.getDataById(this.idForSearch);
    })

  }

  createUser(){
    this.previousPageUrlService.setPreviousUrl(window.location.pathname);
    this.router.navigate(["register"]);
  }

  getDataById(id: string){
    let params: any = {};

    // Add query parameters based on selected options
    if (this.size) {
      params.size = this.size;
    }
    if (this.sortBy) {
      params.sortBy = this.sortBy;
    }
    if (this.search) {
      params.search = this.search;
    }
    params.page = this.page-1;

    if(this.urlPath==="chronicIllness"){
      this.placeholderString = "Search by Patient Name";
      this.chronicIllnessId = parseInt(id);
      this.adminService.getPatientsByChronicIllnessId(this.chronicIllnessId, params).subscribe(data => {
        if(data === null){
          this.noDataFound = true;
        }else {
          this.users = data.content;
          this.totalPages = data.totalPages;
          this.noDataFound = false;
        }
      })
    }
    else if(this.urlPath==="users"){
      this.placeholderString = "Search by Patient Name";
      this.adminService.getAllUsers(params).subscribe(data=>{
        if(data === null){
          this.noDataFound = true;
        } else {
          this.users = data.content;
          this.totalPages = data.totalPages;
          this.noDataFound = false;
        }
      })
    }
    else if(this.urlPath==="doctors"){
      this.placeholderString = "Search by Doctor Name";
      this.adminService.getAllDoctors(params).subscribe(data=> {
        if(data === null){
          this.noDataFound = true;
        } else{
          this.doctors=data.content;
          this.totalPages = data.totalPages;
          this.noDataFound = false;
        }
      })
    }

    else if(this.urlPath === "departments") {
      this.placeholderString = "Search by Doctor Name";
      this.departmentId = parseInt(id);
      this.adminService.getDoctorsByDepartmentId(this.departmentId, params).subscribe(data => {
        console.log(data);  
        if(data === null){
          this.noDataFound = true;
        }else {
          this.doctors = data.content;
          this.totalPages = data.totalPages;
          this.noDataFound = false;
        }
      })
    }
  }

  getSingleUser(id : number | undefined){
    if(this.urlPath ==="users" || this.urlPath==="chronicIllness"){
      this.router.navigate(["dashboard", "users", id]);
    }
    else {
      this.router.navigate(["dashboard","doctors", id])
    }

  }

  onPageSizeChange() {
    this.page = 1;
    this.getDataById(this.idForSearch);
  }

  onSortByChange() {
    this.page = 1;
    this.getDataById(this.idForSearch);
  }

  onSearch() {
    this.page = 1;
    this.getDataById(this.idForSearch);
  }

  onPageChange(pageNumber: number) {
    this.page = pageNumber ;
    this.getDataById(this.idForSearch);
  }
}
