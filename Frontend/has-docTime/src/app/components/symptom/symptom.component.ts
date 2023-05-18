import { Component } from '@angular/core';
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
export class SymptomComponent {
  constructor(private symptomService : SymptomService, private route: ActivatedRoute, private departmentService : DepartmentService){}
  symptoms! : Symptom[];
  ngOnInit(){
    this.symptomService.getSymptoms().subscribe((data)=>{
      console.log(data);
      for(let i=0; i<data.length; i++){
        let departmentArray : Department[] = [];
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
  getAppointmentsFromSymptoms(id: number | undefined) {

  }
}
