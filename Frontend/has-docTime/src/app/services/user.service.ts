import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  isLoggedIn: boolean = false;

  constructor(private http : HttpClient) { }
  baseUrl = "http://localhost:8080/";
  registerUser(user : User){
    this.isLoggedIn = true;
    return this.http.post<User>(`${this.baseUrl}user`, user);
  }
}
