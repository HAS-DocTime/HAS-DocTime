import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { LoginComponent } from '../login/login.component';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { SharedService } from 'src/app/services/shared.service';
import { PastAppointmentService } from 'src/app/services/past-appointment.service';
import { PastAppointment } from 'src/app/models/pastAppointment.model';

@Component({
  selector: 'app-resolved-cases-archive',
  templateUrl: './resolved-cases-archive.component.html',
  styleUrls: ['./resolved-cases-archive.component.css']
})
export class ResolvedCasesArchiveComponent implements OnInit{

  constructor(private pastAppointment: PastAppointmentService, private router: Router, private sharedService: SharedService, private route: ActivatedRoute){}

  id : string = "";
  resolvedCases?: PastAppointment[];

  ngOnInit(): void {
    const token = sessionStorage.getItem('token');
      if (token) {
        let store = token?.split('.');
        this.id = atob(store[1]).split(',')[1].split(':')[1];
        this.id = this.id.substring(1, this.id.length-1);
      };

      this.pastAppointment.getPastAppointmentDataByDoctor(this.id).subscribe((data) =>{
        this.resolvedCases = data;
      });
  }

  caseDetail(id: number){

    this.sharedService.setresolvedCaseDetailedData(this.resolvedCases?.[id]);
    this.router.navigate(['caseDetail'], {relativeTo : this.route});
  }



}
