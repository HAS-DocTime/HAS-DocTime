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

  constructor(private chronicIllnessService: ChronicIllnessService, private router : Router){}

  ngOnInit(){

    this.chronicIllnessService.getAllChronicIllness().subscribe((data) => {
      this.chronicIllnessList = data;
    })
  }

}
