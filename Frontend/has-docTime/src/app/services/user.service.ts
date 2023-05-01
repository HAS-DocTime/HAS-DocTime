import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { Subject } from 'rxjs';
import { Doctor } from '../models/doctor.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  isLoggedIn: Subject<Boolean> = new Subject<Boolean>;
  inSignup: Subject<Boolean> = new Subject<Boolean>;
  inLogin: Subject<Boolean> = new Subject<Boolean>;

  constructor(private http : HttpClient) { }

  baseUrl = "http://localhost:8080/";
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

  logOutUser(){
    localStorage.clear();
    sessionStorage.clear();
    this.isLoggedIn.next(false);
  }

  onCancel(){
    this.isLoggedIn.next(false);
  }

  // getDataSubject(): Subject<any> {
  //   return this.dataSubject;
  // }
}

