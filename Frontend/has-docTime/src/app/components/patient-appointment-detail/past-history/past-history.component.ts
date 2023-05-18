import { Component, Input } from '@angular/core';
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

  constructor(private postAppointmentService: PostAppointmentService){
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

  loadPostAppointment(){
    
  }
}
