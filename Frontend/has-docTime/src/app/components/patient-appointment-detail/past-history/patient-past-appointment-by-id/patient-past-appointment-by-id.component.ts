import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PastAppointment } from 'src/app/models/pastAppointment.model';
import { PastAppointmentService } from 'src/app/services/past-appointment.service';

@Component({
  selector: 'app-patient-past-appointment-by-id',
  templateUrl: './patient-past-appointment-by-id.component.html',
  styleUrls: ['./patient-past-appointment-by-id.component.css']
})
export class PatientPastAppointmentByIdComponent implements OnInit{

  pastAppointment!: PastAppointment;
  id!: string | null;

  constructor(private pastAppointmentService: PastAppointmentService, private route: ActivatedRoute){}

  ngOnInit(): void {
      this.id = this.route.snapshot.paramMap.get('id');
      this.pastAppointmentService.getPastAppointmentDataById(this.id).subscribe(data => {
        this.pastAppointment = data;
      });
  }
}
