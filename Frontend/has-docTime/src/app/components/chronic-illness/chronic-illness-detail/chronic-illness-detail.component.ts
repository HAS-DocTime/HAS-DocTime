import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-chronic-illness-detail',
  templateUrl: './chronic-illness-detail.component.html',
  styleUrls: ['./chronic-illness-detail.component.css']
})
export class ChronicIllnessDetailComponent implements OnInit{

  constructor(private route : ActivatedRoute){}

  ngOnInit(): void {
  }
}
