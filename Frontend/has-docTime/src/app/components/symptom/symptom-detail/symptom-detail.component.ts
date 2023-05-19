import { Component, OnInit } from '@angular/core';
import { SymptomService } from 'src/app/services/symptom.service';

@Component({
  selector: 'app-symptom-detail',
  templateUrl: './symptom-detail.component.html',
  styleUrls: ['./symptom-detail.component.css']
})
export class SymptomDetailComponent implements OnInit{

  symptom! : string;
  constructor(private symptomService: SymptomService) { }

  ngOnInit() {
    this.symptomService.getDiseaseWithCaseCountFromSymptom(this.symptom).subscribe(
      data => {
        console.log(data);
      }
    )
  }


}
