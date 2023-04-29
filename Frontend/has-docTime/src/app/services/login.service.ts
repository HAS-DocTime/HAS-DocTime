import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoginService{

  private loginUrl = "http://localhost:8080/login";

  constructor(private http:HttpClient) {}

  checkDetail(email: string, password: string): Observable<any> {
    const body = { email, password };
    return this.http.post<{token: string}>(this.loginUrl, body);
  }
}
