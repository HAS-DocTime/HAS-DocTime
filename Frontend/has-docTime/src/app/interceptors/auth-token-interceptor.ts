import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable()
export class AuthTokenInterceptor implements HttpInterceptor{

    constructor(){}
    
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        // const authToken = localStorage.getItem('token');
        const authToken = window.sessionStorage.getItem('token');
    

        // let authToken : String = "";  
        // if(localStorage.getItem('token') !== null){
        //     authToken != localStorage.getItem('token');
        // }  
        // console.log(authToken);

        if(authToken){
            // console.log(authToken);
            req = req.clone({
                setHeaders : {
                    Authorization : `Bearer ${authToken}`
                }   
            });
        }

        return next.handle(req);
    }

}