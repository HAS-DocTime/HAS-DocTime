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
      this.chronicIllnessList = data.content;
      this.totalPages = data.totalPages;
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
