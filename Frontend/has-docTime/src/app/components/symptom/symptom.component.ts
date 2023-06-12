import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Department } from 'src/app/models/department.model';
import { Symptom } from 'src/app/models/symptom.model';
import { DepartmentService } from 'src/app/services/department.service';
import { SymptomService } from 'src/app/services/symptom.service';

interface SortByOption {
  label: string;
  value: string;
}

@Component({
  selector: 'app-symptom',
  templateUrl: './symptom.component.html',
  styleUrls: ['./symptom.component.css']
})
export class SymptomComponent implements OnInit{
  constructor(private symptomService : SymptomService, private route: ActivatedRoute, private departmentService : DepartmentService){}
  symptoms! : Symptom[];
  symptom:  string = "";
  pastAppointmentLength: number = 0;
  pastAppointmentLengths: number[] = [];
  page = 1;
  totalPages = 1;
  size = 5;
  sortBy = 'name';
  search = '';
  noDataFound : boolean = false;
  noDataFoundImg : string = "https://firebasestorage.googleapis.com/v0/b/ng-hasdoctime-images.appspot.com/o/dataNotFound.png?alt=media&token=2533f507-7433-4a70-989d-ba861273e537";


  sizeOptions = [5, 10, 15];
  range(totalPages: number): number[] {
    return Array(totalPages).fill(0).map((_, index) => index + 1);
  }

  ngOnInit(){

    this.getData(0);
  }

  getData(page : number) {

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

    this.symptomService.getSymptoms(params).subscribe((data)=>{
      if(data.content.length !== 0){

        for(let i=0; i<data.numberOfElements; i++){
          let departmentArray : Department[] = [];
          this.symptom = data.content[i].name as string;
          this.symptomService.getDiseaseListWithCaseCountFromSymptom(this.symptom).subscribe((data1)=>{
            this.pastAppointmentLength=0;
            if(data1!==null){
              for(let diseaseCaseCount of data1){
                this.pastAppointmentLength += diseaseCaseCount.caseCount;
              }
            }
            data.content[i].caseCount = this.pastAppointmentLength;
          })

          this.symptoms = data.content;
          this.totalPages = data.totalPages;
          this.noDataFound = false;
        }
      } else {
        this.noDataFound = true;
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
