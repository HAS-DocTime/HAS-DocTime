import { Component, OnInit } from '@angular/core';
import { SymptomService } from 'src/app/services/symptom.service';
import { ApexChart, ApexDataLabels, ApexNonAxisChartSeries, ApexTitleSubtitle } from 'ng-apexcharts';
import { ActivatedRoute } from '@angular/router'


@Component({
  selector: 'app-symptom-detail',
  templateUrl: './symptom-detail.component.html',
  styleUrls: ['./symptom-detail.component.css']
})
export class SymptomDetailComponent implements OnInit{

  symptom : string = "";
  constructor(private symptomService: SymptomService, private route : ActivatedRoute) { }

  noDataFound : boolean = false;
  noDataFoundImg : string = "https://firebasestorage.googleapis.com/v0/b/ng-hasdoctime-images.appspot.com/o/dataNotFound.png?alt=media&token=2533f507-7433-4a70-989d-ba861273e537";

  chartSeries: ApexNonAxisChartSeries = [0];

  chartDetails: ApexChart = {
    type: 'donut',
    toolbar: {
      show: false
    }
  };

  chartLabels : string[] = [];

  chartTitle: ApexTitleSubtitle = {
    text: 'Disease Analysis Based on Symptom '+ this.symptom,
    align: 'center'
  };

  chartDataLabels: ApexDataLabels = {
    enabled: true
  };

  plotOptions : Object =  {
    pie: {
      expandOnClick: true
    }
  };

  ngOnInit() {
    this.route.url.subscribe((data)=> {
      // console.log(data)
      this.symptomService.getSymptomById(parseInt(data[1].path)).subscribe((data)=> {

        this.symptom = data.name as string;
        console.log(this.symptom)
        this.symptomService.getDiseaseWithCaseCountFromSymptom(this.symptom).subscribe(
          data => {
            if(data===null){
              this.noDataFound = true;
              return;
            }
            console.log(data);
            this.chartTitle = {
              text: 'Disease Analysis Based on '+ this.symptom,
              align: 'center'
            };
            this.chartSeries = [];
            this.chartLabels = [];
            for(let diseaseCount of data){
              this.chartSeries.push(diseaseCount.caseCount);
              this.chartLabels.push(diseaseCount.disease)
            }
          }
        )
      })
    })

  }


}
