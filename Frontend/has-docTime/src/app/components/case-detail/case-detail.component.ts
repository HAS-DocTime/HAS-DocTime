import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { PastAppointment } from 'src/app/models/pastAppointment.model';
import { SharedService } from 'src/app/services/shared.service';

@Component({
  selector: 'app-case-detail',
  templateUrl: './case-detail.component.html',
  styleUrls: ['./case-detail.component.css']
})
export class CaseDetailComponent implements OnInit{

  caseDetailedData?: PastAppointment | null;

  constructor(private sharedService: SharedService, private location : Location){}

  ngOnInit(): void {
      this.sharedService.getresolvedCaseDetailedData().subscribe((data) => {
        this.caseDetailedData = data;

      });
  }

  navigateBack(){
    this.location.back();
  }

}
