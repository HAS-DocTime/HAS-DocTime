import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MedicalHistory } from 'src/app/models/medicalHistory.model';
import { PostAppointmentService } from 'src/app/services/post-appointment.service';

@Component({
  selector: 'app-past-history',
  templateUrl: './past-history.component.html',
  styleUrls: ['./past-history.component.css']
})
export class PastHistoryComponent {
  postAppointmentDataList?: MedicalHistory[];
  userId?: string | null;
  postAppointmentId!: number | undefined;

  constructor(private postAppointmentService: PostAppointmentService, private router: Router, private route: ActivatedRoute){
  }

  ngOnInit(){

    this.userId = sessionStorage.getItem('userId');
    this.postAppointmentService.getPostAppointmentDataByUser(this.userId).subscribe(
      data => {
        this.postAppointmentDataList = data;
        // this.postAppointmentDataListData.next(this.postAppointmentDataList);
        console.log("data", data);
        
      }
    );

    
  }

  loadPostAppointment(i: number){
    // console.log("print wanted", this.postAppointmentDataList?.[0].id);

    
    this.postAppointmentId = this.postAppointmentDataList?.[i].id;
    this.router.navigate([this.postAppointmentId], {relativeTo: this.route});
  }
}
