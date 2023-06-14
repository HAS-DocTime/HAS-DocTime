import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { Doctor } from '../models/doctor.model';
import { Admin } from '../models/admin.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {


  inSignup: Subject<Boolean> = new Subject<Boolean>;
  inLogin: Subject<Boolean> = new Subject<Boolean>;
  isLoggedIn: Subject<Boolean> = new Subject<Boolean>;

  constructor(private http : HttpClient) { }

  baseUrl = environment.apiUrl;
  registerUser(user : User){
    this.isLoggedIn.next(true);
    sessionStorage.clear();
    localStorage.clear();
    return this.http.post<{token : string}>(`${this.baseUrl}register/user`, user);
  }

  registerDoctor(doctor : Doctor){
    this.isLoggedIn.next(true);
    sessionStorage.clear();
    localStorage.clear();
    return this.http.post<{token : string}>(`${this.baseUrl}register/doctor`, doctor);
  }

  updateUser(user: User, id: number){
    return this.http.put<User>(`${this.baseUrl}user/${id}`, user);
  }

  updateDoctor(doctor: Doctor, id: number){
    return this.http.put<Doctor>(`${this.baseUrl}doctor/${id}`, doctor);
  }

  getUser(id : number){
    return this.http.get<User>(`${this.baseUrl}user/${id}`);
  }

  logOutUser(){
    localStorage.clear();
    sessionStorage.clear();
    this.isLoggedIn.next(false);
  }

  onCancel(){
    this.isLoggedIn.next(false);
  }

  updateEmail(obj : {email : string, id : number, role : string}) : Observable<{token : string}>{
    return this.http.put<{token : string}>(`${this.baseUrl}user/updateEmail`, obj);
  }
}

