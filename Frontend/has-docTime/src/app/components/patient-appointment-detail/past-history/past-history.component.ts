import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Doctor } from 'src/app/models/doctor.model';
import { PastAppointment } from 'src/app/models/pastAppointment.model';
import { DepartmentService } from 'src/app/services/department.service';
import { PastAppointmentService } from 'src/app/services/past-appointment.service';

interface SortByOption {
  label: string;
  value: string;
}

@Component({
  selector: 'app-past-history',
  templateUrl: './past-history.component.html',
  styleUrls: ['./past-history.component.css']
})
export class PastHistoryComponent {
  postAppointmentDataList?: PastAppointment[];
  userId?: string | null;
  postAppointmentId!: number | undefined;
  noDataFound : boolean = false;
  noDataFoundImg : string = "https://firebasestorage.googleapis.com/v0/b/ng-hasdoctime-images.appspot.com/o/dataNotFound.png?alt=media&token=2533f507-7433-4a70-989d-ba861273e537";
  page = 1;
  totalPages = 1;
  size = 5;
  sortBy = 'doctor.user.name';
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

  constructor(private pastAppointmentService: PastAppointmentService, private router: Router, private route: ActivatedRoute, private departmentService : DepartmentService){}

  ngOnInit(){

    this.userId = sessionStorage.getItem('userId');

    this.getData(0);
  }

  getData(page  :number){
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

    this.pastAppointmentService.getPastAppointmentDataByUser(this.userId, params).subscribe(
      data => {
        this.postAppointmentDataList = data;
      }
    );
  }

  loadPostAppointment(i: number){
    this.postAppointmentId = this.postAppointmentDataList?.[i].id;
    this.router.navigate([this.postAppointmentId], {relativeTo: this.route});
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
