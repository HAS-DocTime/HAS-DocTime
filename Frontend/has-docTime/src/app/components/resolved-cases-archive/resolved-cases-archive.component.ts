import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { LoginComponent } from '../login/login.component';
import { PostAppointmentService } from 'src/app/services/post-appointment.service';
import { MedicalHistory } from 'src/app/models/medicalHistory.model';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { SharedService } from 'src/app/services/shared.service';

@Component({
  selector: 'app-resolved-cases-archive',
  templateUrl: './resolved-cases-archive.component.html',
  styleUrls: ['./resolved-cases-archive.component.css']
})
export class ResolvedCasesArchiveComponent implements OnInit{

  constructor(private postAppointment: PostAppointmentService, private router: Router, private sharedService: SharedService, private route: ActivatedRoute){}

  id : string = "";
  resolvedCases?: MedicalHistory[];

  ngOnInit(): void {
    const token = sessionStorage.getItem('token');
      console.log(token);
      if (token) {
        let store = token?.split('.');
        this.id = atob(store[1]).split(',')[1].split(':')[1];
        this.id = this.id.substring(1, this.id.length-1);
      };

      this.postAppointment.getPostAppointmentDataByDoctor(this.id).subscribe((data) =>{
        this.resolvedCases = data;
      });
  }

  caseDetail(id: number){
    console.log("======================================");
    
    this.sharedService.setresolvedCaseDetailedData(this.resolvedCases?.[id]);
    this.router.navigate(['caseDetail'], {relativeTo : this.route});
  }

  

}
