import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import jwtDecode from 'jwt-decode';
import { Observable } from 'rxjs';
import { Token } from '../models/token.model';

@Injectable({
  providedIn: 'root'
})
export class DashboardGuard implements CanActivate {

  constructor(private router: Router){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const token = sessionStorage.getItem('token');

    if(!token){
      return true;
    }else{
      const decodedToken : Token = jwtDecode(token);
      if(decodedToken.role==="DOCTOR"){
        this.router.navigate(['/dashboard', 'doctorScheduleAppointments']);
      }
      else{
        this.router.navigate(['/dashboard', 'appointment']);
      }
      return false;
    }

  }

}
