import { Injectable } from '@angular/core';
import jwtDecode from 'jwt-decode';
import { Token } from '../models/token.model';

@Injectable({
  providedIn: 'root'
})


export class AuthService {

  constructor() { }

  decodeToken() : Token{
    let token : string = sessionStorage.getItem("token") as string;
    let decoded_token : Token= jwtDecode(token);
    return decoded_token;
  }

  
  
}
