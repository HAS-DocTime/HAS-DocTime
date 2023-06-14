import { Component, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Obj } from '@popperjs/core';
import { ListItem } from 'ng-multiselect-dropdown/multiselect.model';
import { trimmedInputValidateSpace } from 'src/app/customValidators/trimmedInputValidateSpace.validator';
import { Department } from 'src/app/models/department.model';
import { FileUpload } from 'src/app/models/fileUpload.model';
import { Symptom } from 'src/app/models/symptom.model';
import { AdminService } from 'src/app/services/admin.service';
import { DepartmentService } from 'src/app/services/department.service';
import { SymptomService } from 'src/app/services/symptom.service';

@Component({
  selector: 'app-create-department',
  templateUrl: './create-department.component.html',
  styleUrls: ['./create-department.component.css']
})
export class CreateDepartmentComponent implements OnInit {

  imageUrl!: string;
  selectedFile: FileUpload | null = null;
  symptomList : Symptom[] = [];
  selectedSymptoms : Symptom[] = [];

  dropdownSettings! : Object;
  createForm!: FormGroup;
  tokenRole : string = "";

  constructor( private departmentService : DepartmentService, private symptomService : SymptomService, private adminService : AdminService, private router : Router){}

  ngOnInit(): void {
    this.initForm();
    this.getSymptoms().then( data => {
      this.symptomList = data;
    });

    this.dropdownSettings = {
      singleSelection: false,
      idField: 'id',
      textField: 'name',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All'
    };
  }

  async getSymptoms(): Promise<Symptom[]> {
    let symptoms: Symptom[] = [];
    await this.symptomService.getSymptomsList().toPromise().then((data) => {
      symptoms = data as Symptom[];
    });
    return symptoms;
  }

  get doctors(): FormArray {
    return this.createForm.get('doctors') as FormArray
  }

  registerDoctor() {
    if(this.tokenRole==='ADMIN') {
      this.router.navigate(["dashboard"])
    }
  }

  // getDocotrs() {
  //   this.adminService.getAllDoctors
  // }


  initForm(){
    this.createForm = new FormGroup({
      name : new FormControl('', [Validators.required, trimmedInputValidateSpace()]),
      timeDuration : new FormControl('', [Validators.required, Validators.max(120), Validators.min(0)]),
      building : new FormControl('', Validators.required),
      description : new FormControl('', Validators.required),
      imageUrl : new FormControl,
      symptoms : new FormArray([]),
      doctors : new FormArray([])
      })
  }


  onSubmit(){
    this.createForm.value.symptoms = this.selectedSymptoms;
    console.log(this.createForm.value);

  }

  onItemSelect($event : ListItem){
    this.selectedSymptoms.push($event as Symptom);
  }

  onItemDeSelect($event : ListItem){
    this.selectedSymptoms = this.selectedSymptoms.filter(data => data.id !== $event.id);
  }




}
