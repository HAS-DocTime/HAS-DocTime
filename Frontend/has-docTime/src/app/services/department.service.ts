import { Injectable } from '@angular/core';
import { Department } from '../models/department.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { PagedObject } from '../models/pagedObject.model';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  constructor(private http : HttpClient) { }

  base_url = environment.apiUrl

  getDepartments(params: any): Observable<any>{
    let httpParams = new HttpParams();
    Object.keys(params).forEach((key) => {
      httpParams = httpParams.set(key, params[key]);
    });
    return this.http.get<PagedObject>(`${this.base_url}department`, { params: httpParams });
  }

  getDepartmentById(id: number){
    return this.http.get<Department>(`${this.base_url}department/${id}`);
  }

  createDepartment( department : Department){
    return this.http.post<Department>(`${this.base_url}department`, department);
  }

  updateDepartment(department : Department, id : number){
    return this.http.put<Department>(`${this.base_url}department/${id}`, department);
  }
}
