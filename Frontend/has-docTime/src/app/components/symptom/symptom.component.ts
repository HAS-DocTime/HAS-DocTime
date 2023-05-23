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
      console.log("symptom data", data);
      for(let i=0; i<data.length; i++){
        let departmentArray : Department[] = [];
        this.symptom = data[i].name as string;
        console.log("symptoms   ", data[i].name)

        this.symptomService.getDiseaseWithCaseCountFromSymptom(this.symptom).subscribe((data)=>{
          console.log(this.symptom);
          console.log(data);
          this.medicalHistoryLength=0;
            if(data!==null){
              for(let diseaseCaseCount of data){
                this.medicalHistoryLength += diseaseCaseCount.caseCount;
                console.log(this.medicalHistoryLength);

              }
            }
            this.medicalHistoryLengths.push(this.medicalHistoryLength);
            console.log("medicalHistoryLengths ", this.medicalHistoryLengths);

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
