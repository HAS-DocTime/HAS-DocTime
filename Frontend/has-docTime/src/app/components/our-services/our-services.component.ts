import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Department } from 'src/app/models/department.model';
import { Token } from 'src/app/models/token.model';
import { AuthService } from 'src/app/services/auth.service';
import { DepartmentService } from 'src/app/services/department.service';

@Component({
  selector: 'app-our-services',
  templateUrl: './our-services.component.html',
  styleUrls: ['./our-services.component.css']
})
export class OurServicesComponent implements OnInit{

  @Input() title = '';

  constructor(private departmentService : DepartmentService, private router : Router, private route : ActivatedRoute, private authService : AuthService){}

  departments : Department[] = [];
  id : number = 0;
  tokenRole: string = "";
  urlPath: string ="";
  page = 1;
  totalPages = 1;
  size = 5;
  sortBy = 'name';
  search = '';
  noDataFound : boolean = false;
  noDataFoundImg : string = "https://firebasestorage.googleapis.com/v0/b/ng-hasdoctime-images.appspot.com/o/dataNotFound.png?alt=media&token=2533f507-7433-4a70-989d-ba861273e537";
  sizeOptions = [5, 10, 15];
  range(totalPages: number): number[] {
    return Array(totalPages).fill(0).map((_, index) => index + 1);
  }

  ngOnInit(){

    const decoded_token : Token = this.authService.decodeToken();

    this.tokenRole = decoded_token.role;
    this.id = parseInt(decoded_token.id);

    this.getData();
  }

  getData(){
    let params: any = {};

    // Add query parameters based on selected options
    if (this.size) {
      params.size = this.size;
    }
    if (this.search) {
      params.search = this.search;
    }
    params.page = this.page-1;

    this.departmentService.getDepartments(params).subscribe((data)=> {
      if(data !== null){
        this.departments = data.content;
        this.totalPages = data.totalPages;
        this.noDataFound = false;
      } else {
        this.noDataFound = true;
      }
    })
  }

  deptDetail(id : number | undefined) {
    if(this.tokenRole==='ADMIN') {
      this.router.navigate(["dashboard", "departments", id])
    }
  }

  createDepartment(){
    this.router.navigate(["dashboard" ,"departments", "createDepartment"])
  }

  onPageSizeChange() {
    this.page = 1;
    this.getData();
  }

  onSearch() {
    this.page = 1;
    this.getData();
  }

  onPageChange(pageNumber: number) {
    this.page = pageNumber ;
    this.getData();
  }
}
