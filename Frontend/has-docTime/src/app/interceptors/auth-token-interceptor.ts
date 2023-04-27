import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Observable } from "rxjs";

export class AuthTokenInterceptor implements HttpInterceptor{

    constructor(private excludedUrls: string[]){}
    
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        if (this.isExcludedUrl(req.url)) {
            return next.handle(req);
          }

        // const authToken = localStorage.getItem('token');
        const authToken = window.sessionStorage.getItem('token');
        

        // let authToken : String = "";  
        // if(localStorage.getItem('token') !== null){
        //     authToken != localStorage.getItem('token');
        // }  
        console.log(authToken);

        if(authToken){
            console.log(authToken);
            req = req.clone({
                setHeaders : {
                    Authorization : `Bearer ${authToken}`
                }
            });
        }

        return next.handle(req);
    }

    private isExcludedUrl(url: string): boolean {
        return this.excludedUrls && this.excludedUrls.includes(url);
      }

}