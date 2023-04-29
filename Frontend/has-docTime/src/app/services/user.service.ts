import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { Subject } from 'rxjs';

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
    return this.http.post<User>(`${this.baseUrl}user`, user);
  }

  logOutUser(){
    this.isLoggedIn.next(false);
  }
}
