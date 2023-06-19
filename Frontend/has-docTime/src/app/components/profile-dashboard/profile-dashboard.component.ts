import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-profile-dashboard',
  templateUrl: './profile-dashboard.component.html',
  styleUrls: ['./profile-dashboard.component.css']
})
export class ProfileDashboardComponent implements OnInit{

  role! : string;
  currentUrl! : string;

  constructor(
    private location : Location, 
    private authService : AuthService, 
    private route: ActivatedRoute,
    private router: Router
    ){}

  ngOnInit(): void {
      this.currentUrl = this.router.url;
      const token = this.authService.decodeToken();
      this.role = token.role;
  }

  navigateBack(){
    this.location.back();
  }
}
