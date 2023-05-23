import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Department } from 'src/app/models/department.model';
import { Symptom } from 'src/app/models/symptom.model';
import { DepartmentService } from 'src/app/services/department.service';
import { SymptomService } from 'src/app/services/symptom.service';

@Component({
  selector: 'app-symptom',
  templateUrl: './symptom.component.html',
  styleUrls: ['./symptom.component.css']
})
export class SymptomComponent implements OnInit{
  constructor(private symptomService : SymptomService, private route: ActivatedRoute, private departmentService : DepartmentService){}
  symptoms! : Symptom[];
  symptom:  string = "";
  medicalHistoryLength: number = 0;
  medicalHistoryLengths: number[] = [];

  ngOnInit(){



    this.symptomService.getSymptoms().subscribe((data)=>{
      for(let i=0; i<data.length; i++){
        let departmentArray : Department[] = [];
        this.symptom = data[i].name as string;
        this.symptomService.getDiseaseWithCaseCountFromSymptom(this.symptom).subscribe((data1)=>{
          this.medicalHistoryLength=0;
            if(data1!==null){
              for(let diseaseCaseCount of data1){
                this.medicalHistoryLength += diseaseCaseCount.caseCount;
              }
            }
            data[i].caseCount=this.medicalHistoryLength;
          })

        const departmentLength : number | undefined = data[i].departments?.length;
        for(let j=0; j<(departmentLength as number); j++){
          let departmentObj : Department | undefined = data[i].departments?.[j];
          if(departmentObj?.id){
            departmentArray.push(departmentObj as Department);
          }
          else{
            this.departmentService.getDepartmentById(departmentObj as number).subscribe(
              (data)=> {
                let dep = data;
                departmentArray.push(dep);
              }
            );
          }
        }
        data[i].departments = departmentArray;
      }

      this.symptoms = data;
    })
  }

  getCaseCount(symptom : string | undefined){

  }
}
