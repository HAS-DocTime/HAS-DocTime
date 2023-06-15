import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, Subject } from "rxjs";
import { environment } from "src/environments/environment";
import { VerifyOtp } from "../models/verifyOtp.model";

@Injectable({
  providedIn: 'root'
})
export class LoginService{

  isLoggedIn: Subject<Boolean> = new Subject<Boolean>;

  private loginUrl = `${environment.apiUrl}login`;

  constructor(private http:HttpClient) {}

  checkDetail(email: string, password: string): Observable<{'token': string}> {
    const body = { "email" : email, "password" : password };
    this.isLoggedIn.next(true);

    return this.http.post<{token : string}>(this.loginUrl, body);
  }

  sendOtpMail(email : string){
    return this.http.post(`${this.loginUrl}/forgotPassword`, email);
  }

  verifyOtp(verifyOtpBody : VerifyOtp){
    return this.http.post(`${this.loginUrl}/otpVerify`, verifyOtpBody);
  }
}
