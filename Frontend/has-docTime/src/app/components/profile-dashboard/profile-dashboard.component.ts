import { Location } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-profile-dashboard',
  templateUrl: './profile-dashboard.component.html',
  styleUrls: ['./profile-dashboard.component.css']
})
export class ProfileDashboardComponent {

  constructor(private location : Location){}

  navigateBack(){
    this.location.back();
  }
}
