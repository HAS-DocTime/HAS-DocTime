import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route } from '@angular/router';
import { Department } from 'src/app/models/department.model';
import { DepartmentService } from 'src/app/services/department.service';

@Component({
  selector: 'app-dept-detail',
  templateUrl: './dept-detail.component.html',
  styleUrls: ['./dept-detail.component.css']
})
export class DeptDetailComponent implements OnInit{

  constructor(private departmentService: DepartmentService, private route: ActivatedRoute, private location : Location) { }

  id!: number;
  department!: Department;
  urlPath! : string;

  ngOnInit(){

      this.route.params.subscribe((data) => {
        this.id = parseInt(data['id']);
        this.departmentService.getDepartmentById(this.id).subscribe((data) => {
          this.department = data;
        })
      });

  }

  navigateBack(){
    this.location.back();
  }
}
