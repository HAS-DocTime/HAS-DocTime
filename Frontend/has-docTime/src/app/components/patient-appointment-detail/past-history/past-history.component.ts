import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Doctor } from 'src/app/models/doctor.model';
import { PastAppointment } from 'src/app/models/pastAppointment.model';
import { DepartmentService } from 'src/app/services/department.service';
import { PastAppointmentService } from 'src/app/services/past-appointment.service';

@Component({
  selector: 'app-past-history',
  templateUrl: './past-history.component.html',
  styleUrls: ['./past-history.component.css']
})
export class PastHistoryComponent {
  postAppointmentDataList?: PastAppointment[];
  userId?: string | null;
  postAppointmentId!: number | undefined;

  constructor(private pastAppointmentService: PastAppointmentService, private router: Router, private route: ActivatedRoute, private departmentService : DepartmentService){}

  ngOnInit(){

    this.userId = sessionStorage.getItem('userId');
    this.pastAppointmentService.getPastAppointmentDataByUser(this.userId).subscribe(
      data => {
        for(let appointment of data){
          if(!appointment?.doctor?.department?.id){
            this.departmentService.getDepartmentById(appointment.doctor?.department as number).subscribe((data)=> {
              (appointment.doctor as Doctor ).department = data;
            });
          }
        }
        this.postAppointmentDataList = data;
      }
    );
  }

  loadPostAppointment(i: number){
    this.postAppointmentId = this.postAppointmentDataList?.[i].id;
    this.router.navigate([this.postAppointmentId], {relativeTo: this.route});
  }
}
