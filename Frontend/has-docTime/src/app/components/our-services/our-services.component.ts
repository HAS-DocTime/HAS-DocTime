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

  ngOnInit(){

    const token = sessionStorage.getItem('token');
    if (token) {

      let store = token?.split('.');
      this.tokenRole = atob(store[1]).split(',')[2].split(':')[1];

      this.id = parseInt(atob(store[1]).split(',')[1].split(':')[1].substring(1, this.tokenRole.length - 1));

      this.tokenRole = this.tokenRole.substring(1, this.tokenRole.length - 1);
    }

    this.departmentService.getDepartments().subscribe((data)=> {
      this.departments = data;
    })
  }



  deptDetail(id : number | undefined) {
    if(this.tokenRole==='ADMIN') {
      this.router.navigate(["dashboard", "departments", id])
    }
  }
}
