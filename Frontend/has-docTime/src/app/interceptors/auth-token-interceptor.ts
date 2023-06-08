import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable()
export class AuthTokenInterceptor implements HttpInterceptor{

    constructor(){}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        const authToken = window.sessionStorage.getItem('token');

        if(authToken){
            req = req.clone({
                setHeaders : {
                    Authorization : `Bearer ${authToken}`
                }
            });
        }
        return next.handle(req);
    }
}
