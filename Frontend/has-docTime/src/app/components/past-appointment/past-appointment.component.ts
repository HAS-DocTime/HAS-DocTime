import { Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PagedObject } from 'src/app/models/pagedObject.model';
import { PastAppointment } from 'src/app/models/pastAppointment.model';
import { Symptom } from 'src/app/models/symptom.model';
import { Token } from 'src/app/models/token.model';
import { AuthService } from 'src/app/services/auth.service';
import { PastAppointmentService } from 'src/app/services/past-appointment.service';
import { SymptomService } from 'src/app/services/symptom.service';

interface SortByOption {
  label: string;
  value: string;
}

@Component({
  selector: 'app-past-appointments',
  templateUrl: './past-appointment.component.html',
  styleUrls: ['./past-appointment.component.css']
})
export class PastAppointmentComponent implements OnInit{


  constructor(private pastAppointmentService: PastAppointmentService, private symptomService: SymptomService, private router : Router, private route : ActivatedRoute, private authService : AuthService){}

  id : number = 0;
  tokenRole : string = "";
  symptomId: number = 0;
  symptom! : Symptom;
  noDataFound : boolean = false;
  noDataFoundImg : string = "https://firebasestorage.googleapis.com/v0/b/ng-hasdoctime-images.appspot.com/o/dataNotFound.png?alt=media&token=2533f507-7433-4a70-989d-ba861273e537";
  pastAppointmentList: PastAppointment[]=[];
  page = 1;
  totalPages = 1;
  size = 5;
  sortBy = '';
  search = '';

  sizeOptions = [5, 10, 15];
  range(totalPages: number): number[] {
    return Array(totalPages).fill(0).map((_, index) => index + 1);
  }

  sortByOptions: SortByOption[] = [
    { label: 'StartTime', value: 'timeSlotForAppointmentData.startTime' },
    { label: 'Doctor Name', value: 'doctor.user.name' },
    { label: 'Patient Name', value: 'user.name'}
  ];


  ngOnInit() {
    const decoded_token : Token = this.authService.decodeToken();

    this.tokenRole = decoded_token.role;
    this.id = parseInt(decoded_token.id);

    this.getData(0);
  }

  getData(page : number){
    let params: any = {};

    // Add query parameters based on selected options
    if (this.size) {
      params.size = this.size;
    }
    if (this.sortBy) {
      params.sortBy = this.sortBy;
    }
    if (this.search) {
      params.search = this.search;
    }
    params.page = this.page-1;

    if(this.tokenRole==="ADMIN"){
      this.route.url.subscribe((data)=>{
        if(data[0].path === "symptoms"){
          this.symptomId = parseInt(data[1].path);
          this.symptomService.getSymptomById(this.symptomId).subscribe((data)=> {
            if(data === null){
              this.noDataFound = true;
            }
            else{
              this.symptom = data;
              this.symptomService.getPastAppointmentsFromSymptom(this.symptom?.name, params).subscribe((data)=>{
                if(data===null){
                  this.noDataFound = true;
                } else{
                  this.pastAppointmentList = data.content;
                  this.totalPages = data.totalPages;
                  this.noDataFound = false;
                }
              })
            }

         })
        }
        else{
          this.pastAppointmentService.getPastAppointmentData(params)
          .subscribe(
            (data: any) => {
              if(data===null){
                this.noDataFound = true;
              } else {
                this.pastAppointmentList = data.content;
                this.totalPages = data.totalPages;
                this.noDataFound = false;
              }

            },
            (error: any) => {
              console.error('Error getting past appointment:', error);
            }
          );
        }
      })


    }

    else{
      this.sortByOptions.pop();

      this.pastAppointmentService.getPastAppointmentDataByUserEmail(params)
    .subscribe(
      (data: any) => {
        if(data===null){
          this.noDataFound = true;
        }
        else{
          this.pastAppointmentList = data.content;
          this.totalPages = data.totalPages;
          this.noDataFound = false;
        }
      },
      (error: any) => {
        console.error('Error getting past appointments history:', error);
      }
    );
    }
  }

  getdetailedHistory(id : number | undefined){

    this.router.navigate(["dashboard", "pastAppointments", id]);
  }

  onPageSizeChange() {
    this.page = 1;
    this.getData(this.page);
  }

  onSortByChange() {
    this.page = 1;
    this.getData(this.page);
  }

  onSearch() {
    this.page = 1;
    this.getData(this.page);
  }

  onPageChange(pageNumber: number) {
    this.page = pageNumber ;
    this.getData(this.page);
  }
}
