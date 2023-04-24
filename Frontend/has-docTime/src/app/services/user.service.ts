import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http : HttpClient) { }
  baseUrl = "http://localhost:8080/";
  registerUser(user : User){
    this.http.post<User>(`${this.baseUrl}user`, user).subscribe(
      (data) => {
        console.log(data);
      }
    )
  }
}
