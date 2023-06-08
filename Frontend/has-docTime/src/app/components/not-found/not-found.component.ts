import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import gsap, { Power0 } from 'gsap';
import jwtDecode from 'jwt-decode';
import { Token } from 'src/app/models/token.model';

@Component({
  selector: 'app-not-found',
  templateUrl: './not-found.component.html',
  styleUrls: ['./not-found.component.css']
})
export class NotFoundComponent implements OnInit{

  constructor(private router : Router){}

  ngOnInit(): void {
    const tl = gsap.timeline({ repeat: -1, yoyo: true });

    tl.fromTo('#left-eyeboll', { x: 3 }, { x: -3, duration: 0.5, ease: Power0.easeNone })
    .fromTo('#right-eyeboll', { x: 3 }, { x: -3, duration: 0.5, ease: Power0.easeNone }, 0);
  }

  redirectToHomePage(){
    const token = sessionStorage.getItem("token");
    if(token){
      const decoded_token : Token = jwtDecode(token);
      if(decoded_token.role === "PATIENT" || decoded_token.role === "ADMIN"){
        this.router.navigate(["/dashboard", "appointment"]);
      }
      else if(decoded_token.role === "DOCTOR"){
        this.router.navigate(["/dashboard", "doctorScheduleAppointments"]);
      }
    }
    else{
      this.router.navigate(["/"]);
    }

  }





}
