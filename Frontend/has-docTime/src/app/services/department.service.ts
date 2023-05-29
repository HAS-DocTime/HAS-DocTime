import { Injectable } from '@angular/core';
import { Department } from '../models/department.model';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  constructor(private http : HttpClient) { }

  base_url = environment.apiUrl

  getDepartments(){
    return this.http.get<Department[]>(`${this.base_url}department`);
  }

  getDepartmentById(id: number){
    return this.http.get<Department>(`${this.base_url}department/${id}`);
  }
}
