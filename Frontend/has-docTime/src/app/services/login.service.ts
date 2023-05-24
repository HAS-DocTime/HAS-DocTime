import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, Subject } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoginService{

  isLoggedIn: Subject<Boolean> = new Subject<Boolean>;

  private loginUrl = `http://192.1.200.29:8080/login`;

  constructor(private http:HttpClient) {}

  checkDetail(email: string, password: string): Observable<{'token': string}> {
    const body = { "email" : email, "password" : password };
    this.isLoggedIn.next(true);

    return this.http.post<{token : string}>(this.loginUrl, body);
  }
}
