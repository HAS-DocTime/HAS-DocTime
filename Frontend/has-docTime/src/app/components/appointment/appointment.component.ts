import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Appointment } from 'src/app/models/appointment.model';
import { Department } from 'src/app/models/department.model';
import { Doctor } from 'src/app/models/doctor.model';
import { AppointmentService } from 'src/app/services/appointment.service';
import { DepartmentService } from 'src/app/services/department.service';
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

  constructor(private appointmentService : AppointmentService, private userService : UserService, private router : Router, private route : ActivatedRoute, private departmentService : DepartmentService){}

  appointments : Appointment[] = [];

  id : number = 0;
  tokenRole : string = "";
  page = 1;
  totalPages = 1;
  size = 5;
  sortBy = 'user.name';
  search = '';
  urlPath!: string;

  sizeOptions = [5, 10, 15];
  range(totalPages: number): number[] {
    return Array(totalPages).fill(0).map((_, index) => index + 1);
  }

  sortByOptions: SortByOption[] = [
    { label: 'StartTime', value: 'timeSlotForAppointment.startTime' },
    { label: 'Doctor Name', value: 'doctor.user.name' }
  ];


  ngOnInit(){

    const token = sessionStorage.getItem('token');
    if (token) {

      let store = token?.split('.');
      this.tokenRole = atob(store[1]).split(',')[2].split(':')[1];

      this.id = parseInt(atob(store[1]).split(',')[1].split(':')[1].substring(1, this.tokenRole.length - 1));

      this.tokenRole = this.tokenRole.substring(1, this.tokenRole.length - 1);
    }
    this.route.url.subscribe((data) => {
      this.urlPath = data[0].path

    })

    if ((this.tokenRole === 'DOCTOR' && this.urlPath !== 'appointment')|| this.tokenRole === 'ADMIN') {
      this.sortByOptions.push({ label: 'Patient Name', value: 'user.name' });
    }

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

    if(this.tokenRole==='ADMIN'){
        this.appointmentService.getAppointments(params).subscribe((data)=>{
          for(let appointment of data.content){
            if(!appointment?.doctor?.department?.id){
              this.departmentService.getDepartmentById(appointment.doctor?.department as number).subscribe((data)=> {
                (appointment.doctor as Doctor).department = data;
              });
            }
          }
          this.appointments = data.content;
          this.totalPages = data.totalPages;
        })
      }
      else {
        this.appointmentService.getAppointmentByUser((this.id.toString()), params).subscribe((data)=> {
          for(let appointment of data.content){
            if(!appointment?.doctor?.department?.id){
              this.departmentService.getDepartmentById(appointment.doctor?.department as number).subscribe((data)=> {
                (appointment.doctor as Doctor).department = data;
              });
            }
          }
          this.appointments = data.content;
          this.totalPages = data.totalPages;
        });
      }

    }


  appointmentDetails(id : number | undefined){
    this.router.navigate([id], {relativeTo : this.route})
  }

  deleteAppointment(id : number | undefined){
    this.appointmentService.deleteAppointment(id).subscribe((data)=> {
        if(this.tokenRole==="ADMIN"){
          this.appointmentService.getAppointmentList().subscribe((data)=> {
            for(let appointment of data){
              if(!appointment?.doctor?.department?.id){
                this.departmentService.getDepartmentById(appointment.doctor?.department as number).subscribe((data)=> {
                  (appointment.doctor as Doctor).department = data;
                });
              }
            }
            this.appointments = data;
          });
        }
        else{
          this.appointmentService.getAppointmentListByUser((this.id?.toString())).subscribe((data)=> {
            for(let appointment of data){
              if(!appointment?.doctor?.department?.id){
                this.departmentService.getDepartmentById(appointment.doctor?.department as number).subscribe((data)=> {
                  (appointment.doctor as Doctor).department = data;
                });
              }
            }
            this.appointments = data;
          });
        }
    })
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
