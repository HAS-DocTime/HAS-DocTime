import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Appointment } from 'src/app/models/appointment.model';
import { Token } from 'src/app/models/token.model';
import { AppointmentService } from 'src/app/services/appointment.service';
import { AuthService } from 'src/app/services/auth.service';
import { SharedService } from 'src/app/services/shared.service';

interface SortByOption {
  label: string;
  value: string;
}
@Component({
  selector: 'app-doctor-schedule-appointments',
  templateUrl: './doctor-schedule-appointments.component.html',
  styleUrls: ['./doctor-schedule-appointments.component.css']
})
export class DoctorScheduleAppointmentsComponent {

  constructor(private appointmentService: AppointmentService,
    private router : Router,
    private route : ActivatedRoute,
    private sharedService: SharedService,
    private authService : AuthService
    ){}

  appointments: Appointment[]=[];
  id: string = "";
  page = 1;
  totalPages = 1;
  size = 5;
  sortBy = 'user.name';
  search = '';
  noDataBySearch: Boolean = false;

  sizeOptions = [5, 10, 15];
  range(totalPages: number): number[] {
    return Array(totalPages).fill(0).map((_, index) => index + 1);
  }

  sortByOptions: SortByOption[] = [
    { label: 'StartTime', value: 'timeSlotForAppointment.startTime' },
    { label: 'Patient Name', value: 'user.name' }
  ];

  ngOnInit() {
    const decoded_token : Token = this.authService.decodeToken();
    this.id = decoded_token.id;
      this.getData();
  }

  getData(){

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

    this.appointmentService.getAppointmentsByDoctor(this.id, params)
    .subscribe(
      (data) => {
        if(data === null){
          this.noDataBySearch = true;
          this.appointments = [];
        } else{
          this.noDataBySearch = false;
          this.appointments = data.content;
          this.totalPages = data.totalPages;
        }
      },
      (error: any) => {
        console.error('Error getting doctor apppointments', error);
      }
    );
  }

  getdetailedHistory(id : any){

    this.router.navigate([id], {relativeTo : this.route});
  }

  appointmentDetail(i : number){

    sessionStorage.setItem('userId', this.appointments[i].user.id?.toString() as string);
    sessionStorage.setItem('appointmentId', this.appointments[i].id.toString());
    this.router.navigate(['patientAppointmentDetail/appointmentDetails'], {relativeTo:this.route});
  }

  onPageSizeChange() {
    this.page = 1;
    this.getData();
  }

  onSortByChange() {
    this.page = 1;
    this.getData();
  }

  onSearch() {
    this.page = 1;
    this.getData();
  }

  onPageChange(pageNumber: number) {
    this.page = pageNumber ;
    this.getData();
  }
}
