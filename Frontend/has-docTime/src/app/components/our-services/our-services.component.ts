import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Department } from 'src/app/models/department.model';
import { DepartmentService } from 'src/app/services/department.service';

@Component({
  selector: 'app-our-services',
  templateUrl: './our-services.component.html',
  styleUrls: ['./our-services.component.css']
})
export class OurServicesComponent implements OnInit{

  @Input() title = '';

  constructor(private departmentService : DepartmentService, private router : Router, private route : ActivatedRoute){}

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

    const token = sessionStorage.getItem('token');
    if (token) {

      let store = token?.split('.');
      this.tokenRole = atob(store[1]).split(',')[2].split(':')[1];

      this.id = parseInt(atob(store[1]).split(',')[1].split(':')[1].substring(1, this.tokenRole.length - 1));

      this.tokenRole = this.tokenRole.substring(1, this.tokenRole.length - 1);
    }
    this.route.url.subscribe((data) => {
      this.urlPath = data[0].path;
      console.log(this.urlPath);

    })

    this.getData(0);
  }

  getData(page: number){
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
    this.getData(this.page);
  }

  onSearch() {
    this.page = 1;
    this.getData(this.page);
  }

  onPageChange(pageNumber: number) {
    this.page = pageNumber ;
    this.getData(this.page);
  }
}
