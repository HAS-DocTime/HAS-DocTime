import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PastAppointmentService } from 'src/app/services/past-appointment.service';

@Component({
  selector: 'app-detailed-history',
  templateUrl: './detailed-past-appointment.component.html',
  styleUrls: ['./detailed-past-appointment.component.css']
})
export class DetailedPastAppointmentComponent {
  constructor(private route : ActivatedRoute, private pastAppointmentService : PastAppointmentService, private router : Router, private location : Location){}

  public id! : string | null;

  public pastAppointment?: any;
  symptoms: string[] | undefined = [];
  tokenRole : string = "";

  ngOnInit() {
    const token = sessionStorage.getItem('token');
    if (token) {
      let store = token?.split('.');
      this.tokenRole = atob(store[1]).split(',')[2].split(':')[1];
      this.tokenRole = this.tokenRole.substring(1, this.tokenRole.length - 1);
    }

    this.id = this.route.snapshot.paramMap.get('id');
    this.pastAppointmentService.getPastAppointmentDataById(this.id)
    .subscribe((data)=> {
      this.pastAppointment = data;
      this.symptoms = data.symptoms?.split("; ");
    })
  }

  backToPastAppointments(){
    this.router.navigate(['../'], {relativeTo : this.route});
  }

  navigateBack(){
    this.location.back();
  }
}
