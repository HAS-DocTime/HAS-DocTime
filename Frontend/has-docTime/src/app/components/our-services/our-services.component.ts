import { Component, OnInit } from '@angular/core';
import { Department } from 'src/app/models/department.model';
import { DepartmentService } from 'src/app/services/department.service';

@Component({
  selector: 'app-our-services',
  templateUrl: './our-services.component.html',
  styleUrls: ['./our-services.component.css']
})
export class OurServicesComponent implements OnInit{
  constructor(private departmentService : DepartmentService){}

  departments : Department[] = [];

  ngOnInit(){
    this.departmentService.getDepartments().subscribe((data)=> {
      this.departments = data;
    })
  }
}
