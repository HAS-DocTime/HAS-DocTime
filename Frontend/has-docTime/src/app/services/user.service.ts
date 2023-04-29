import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { Subject } from 'rxjs';
import { LoginDetails } from '../models/login-details.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  // private dataSubject = new Subject<any>();
  isLoggedIn: Subject<Boolean> = new Subject<Boolean>;
  inSignupForm: Subject<Boolean> = new Subject<Boolean>;

  constructor(private http : HttpClient) { }
  baseUrl = "http://localhost:8080/";
  // registerUser(user : LoginDetails){
  //   this.isLoggedIn.next(true);
  //   return this.http.post<string>(`${this.baseUrl}auth/register`, user);
   
  // }
  registerUser(user : LoginDetails){
    this.isLoggedIn.next(true);
    sessionStorage.clear();
    localStorage.clear();
    return this.http.post<{token : string}>(`${this.baseUrl}auth/register`, user);
  }
  createUser(user : User){
    return this.http.post<User>(`${this.baseUrl}user`, user);
  }

  onLogout(){
    localStorage.clear();
    sessionStorage.clear();
    this.isLoggedIn.next(false);
  }

  onCancel(){
    this.isLoggedIn.next(false);
    this.inSignupForm.next(false);
  }

  // getDataSubject(): Subject<any> {
  //   return this.dataSubject;
  // }
}

