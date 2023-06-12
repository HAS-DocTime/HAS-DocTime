import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ChronicIllness } from 'src/app/models/chronicIllness.model';
import { ChronicIllnessService } from 'src/app/services/chronic-illness.service';

@Component({
  selector: 'app-chronic-illness',
  templateUrl: './chronic-illness.component.html',
  styleUrls: ['./chronic-illness.component.css']
})
export class ChronicIllnessComponent implements OnInit{

  chronicIllnessList! : ChronicIllness[];
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

  constructor(private chronicIllnessService: ChronicIllnessService, private router : Router){}

  ngOnInit(){

    this.getData(0);
  }

  getData(page : number){
    let params: any = {};

    // Add query parameters based on selected options
    if (this.size) {
      params.size = this.size;
    }
    if (this.search) {
      params.search = this.search;
    }
    params.page = this.page-1;

    this.chronicIllnessService.getAllChronicIllness(params).subscribe((data) => {
      if(data===null){
        this.noDataFound= true;
      } else {
        this.noDataFound= false;
        this.chronicIllnessList = data.content;
        this.totalPages = data.totalPages;
      }

    })
  }

  onPageSizeChange() {
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
