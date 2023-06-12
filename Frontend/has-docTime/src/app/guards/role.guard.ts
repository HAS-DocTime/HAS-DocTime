import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import jwtDecode from 'jwt-decode';
import { Observable } from 'rxjs';
import { Token } from '../models/token.model';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private router: Router ){}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

      const requiredRoles = route.data['roles'] as string[];
      const token = sessionStorage.getItem('token')
      const decodedToken: Token = jwtDecode(token as string);
      const userRole = decodedToken.role;
      if(userRole){
        const hasRequiredRole = requiredRoles.includes(userRole);
        if(hasRequiredRole){
          return true;
        }
      }
      this.router.navigate(['/notFound']);
    return false;
  }

}
