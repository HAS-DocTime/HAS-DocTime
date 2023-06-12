import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Appointment } from 'src/app/models/appointment.model';
import { Doctor } from 'src/app/models/doctor.model';
import { Token } from 'src/app/models/token.model';
import { AppointmentService } from 'src/app/services/appointment.service';
import { AuthService } from 'src/app/services/auth.service';
import { DepartmentService } from 'src/app/services/department.service';
import { DynamicTimeZoneService } from 'src/app/services/dynamic-time-zone.service';
import { ToastMessageService } from 'src/app/services/toast-message.service';
import { UserService } from 'src/app/services/user.service';

interface SortByOption {
  label: string;
  value: string;
}

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})

export class AppointmentComponent implements OnInit{

  constructor(private appointmentService : AppointmentService, private userService : UserService, private router : Router, private route : ActivatedRoute, private departmentService : DepartmentService, private toast : ToastMessageService, private authService : AuthService, private dynamicTimeSlotService : DynamicTimeZoneService){}

  appointments : Appointment[] = [];
  id : number = 0;
  tokenRole : string = "";
  page = 1;
  totalPages = 0;
  size = 5;
  sortBy = 'user.name';
  search = '';
  urlPath!: string;
  totalElements: number = 0;
  sizeOptions = [5, 10, 15];
  range(totalPages: number): number[] {
    return Array(totalPages).fill(0).map((_, index) => index + 1);
  }
  sortByOptions: SortByOption[] = [
    { label: 'StartTime', value: 'timeSlotForAppointment.startTime' },
    { label: 'Doctor Name', value: 'doctor.user.name' }
  ];
  params : any = {};

  ngOnInit(){

    this.dynamicTimeSlotService.convertToTimeZone(100);

    const decoded_token : Token = this.authService.decodeToken();

    this.tokenRole = decoded_token.role;
    this.id = parseInt(decoded_token.id);
    
    this.route.url.subscribe((data) => {
      this.urlPath = data[0].path

    })

    if ((this.tokenRole === 'DOCTOR' && this.urlPath !== 'appointment')|| this.tokenRole === 'ADMIN') {
      this.sortByOptions.push({ label: 'Patient Name', value: 'user.name' });
    }

    this.getData();

  }

  getData(){
    // Add query parameters based on selected options
    if (this.size) {
      this.params.size = this.size;
    }
    if (this.sortBy) {
      this.params.sortBy = this.sortBy;
    }
    if (this.search) {
      this.params.search = this.search;
    }
    else{
      this.params.search = "";
    }
    this.params.page = this.page-1;

    if(this.tokenRole==='ADMIN'){
        this.appointmentService.getAppointments(this.params).subscribe((data)=>{
          this.appointments = data.content as Appointment[];
          this.totalPages = data.totalPages;
        })
      }
    else {
      this.appointmentService.getAppointmentByUser((this.id.toString()), this.params).subscribe((data)=> {
        if(data){
          this.appointments = data.content as Appointment[];
          this.totalPages = data.totalPages;
        }
        else{
          this.appointments = [];
          this.totalPages = 0;
        }
      });
    }

    }


  appointmentDetails(id : number | undefined){
    this.router.navigate([id], {relativeTo : this.route})
  }

  deleteAppointment(id : number | undefined){
    if (this.size) {
      this.params.size = this.size;
    }
    if (this.sortBy) {
      this.params.sortBy = this.sortBy;
    }
    if (this.search) {
      this.params.search = this.search;
    }
    this.params.page = this.page-1;
    this.appointmentService.deleteAppointment(id).subscribe((data)=> {
        if(this.tokenRole==="ADMIN"){
          this.appointmentService.getAppointments(this.params).subscribe((data)=> {
            this.appointments = data.content as Appointment[];
            this.totalPages = data.totalPages;
          });
        }
        else{
          this.appointmentService.getAppointmentByUser(this.id.toString(), this.params).subscribe((data)=> {
            this.appointments = data.content as Appointment[];
            this.totalPages = data.totalPages;
          });
        }
        this.toast.showError(`The Appointment is deleted`, "Appointment deleted");
    })
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
