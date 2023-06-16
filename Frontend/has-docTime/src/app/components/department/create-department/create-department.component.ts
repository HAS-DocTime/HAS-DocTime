import { Component, OnInit } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/compat/storage';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ListItem } from 'ng-multiselect-dropdown/multiselect.model';
import { finalize } from 'rxjs';
import { trimmedInputValidateSpace } from 'src/app/customValidators/trimmedInputValidateSpace.validator';
import { Department } from 'src/app/models/department.model';
import { Doctor } from 'src/app/models/doctor.model';
import { FileUpload } from 'src/app/models/fileUpload.model';
import { Symptom } from 'src/app/models/symptom.model';
import { Token } from 'src/app/models/token.model';
import { AdminService } from 'src/app/services/admin.service';
import { AuthService } from 'src/app/services/auth.service';
import { DepartmentService } from 'src/app/services/department.service';
import { PreviousPageUrlServiceService } from 'src/app/services/previous-page-url-service.service';
import { SymptomService } from 'src/app/services/symptom.service';
import { ToastMessageService } from 'src/app/services/toast-message.service';

interface SortByOption {
  label: string;
  value: string;
}

@Component({
  selector: 'app-create-department',
  templateUrl: './create-department.component.html',
  styleUrls: ['./create-department.component.css']
})
export class CreateDepartmentComponent implements OnInit {

  departmentImage!: string;
  selectedFile: FileUpload | null = null;
  symptomList : Symptom[] = [];
  selectedSymptoms : number[] = [];
  dropdownSettings! : Object;
  createForm!: FormGroup;
  tokenRole : string = "";
  page = 1;
  doctorsList! : Doctor[];
  selectedDoctorIds: number[] = [];
  newDepartment? : Department;
  imageUrl!: string;

  totalPages = 1;
  size = 10;
  sortBy = 'user.name';
  search = '';
  noDataFound : boolean = false;
  noDataFoundImg : string = "https://firebasestorage.googleapis.com/v0/b/ng-hasdoctime-images.appspot.com/o/dataNotFound.png?alt=media&token=2533f507-7433-4a70-989d-ba861273e537";
  isDivOpen = false;
  sizeOptions = [5, 10, 15];
  sortByOptions: SortByOption[] = [
    { label: 'Doctor Name', value: 'user.name' },
    { label: 'Department', value: 'Department.name' }
  ];
  range(totalPages: number): number[] {
    return Array(totalPages).fill(0).map((_, index) => index + 1);
  }

  constructor( private departmentService : DepartmentService, private symptomService : SymptomService, private adminService : AdminService, private router : Router, private authService: AuthService, private previousPageUrlService : PreviousPageUrlServiceService, private toast : ToastMessageService, private storage: AngularFireStorage,){}

  ngOnInit(): void {
    this.initForm();

    const decoded_token : Token = this.authService.decodeToken();

    this.tokenRole = decoded_token.role;

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
      console.log(data);

    });
    return symptoms;
  }

  get doctors(): FormArray {
    return this.createForm.get('doctors') as FormArray
  }

  doctorSelected(doctorId: number | undefined): boolean {
    return this.selectedDoctorIds.includes(doctorId as number);
  }

  toggleDoctorSelection(doctorId: number | undefined) {
    if (this.doctorSelected(doctorId as number)) {
      this.selectedDoctorIds = this.selectedDoctorIds.filter(id => id !== doctorId);
    } else {
      this.selectedDoctorIds.push(doctorId as number);
    }

  }

  registerDoctor() {
    if(this.tokenRole==='ADMIN') {
      this.previousPageUrlService.setPreviousUrl(window.location.pathname);
      this.router.navigate(["register"]);
    }
  }

  getAllDoctors() {
    this.isDivOpen = true;
    let params: any = {};

    // Add query parameters based on selected options
    if (this.size) {
      params.size = this.size;
    }
    if (this.sortBy) {
      params.sortBy = this.sortBy;
    }
    if (this.search) {
      params.search = this.search;
    }
    params.page = this.page-1;
    this.adminService.getAllDoctors(params).subscribe(data=> {
      console.log(data);

      if(data === null){
        this.noDataFound = true;
      } else{
        this.doctorsList=data.content;
        this.totalPages = data.totalPages;
        this.noDataFound = false;
      }
    })
  }


  initForm(){
    this.createForm = new FormGroup({
      name : new FormControl('', [Validators.required, trimmedInputValidateSpace()]),
      timeDuration : new FormControl('', [Validators.required, Validators.max(120), Validators.min(0)]),
      building : new FormControl('', Validators.required),
      description : new FormControl('', Validators.required),
      departmentImage : new FormControl,
      symptoms : new FormArray([]),
      doctors : new FormArray([])
      })
  }

  setuserImageUrl(department: Department, id : number) {
    this.departmentService.updateDepartment(department, id).subscribe((data) => {
    });
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    const reader = new FileReader();
    console.log("selectedFile: " , this.selectedFile);

    reader.onload = (e: any) => {
      this.imageUrl = e.target.result;
    };
    if (this.selectedFile) {
      reader.readAsDataURL(event.target.files[0]);
    }
  }

  onSubmit(){
    this.createForm.value.symptoms = this.selectedSymptoms.map(id => ({id}));
    this.createForm.value.doctors = this.selectedDoctorIds.map(id => ({id}));
    console.log(this.createForm.value);
    let department : Department;
    department = this.createForm.value;

     this.departmentService.createDepartment(department).subscribe((data) => {
      this.router.navigate(['dashboard/departments' ,data.id]);
      this.newDepartment = data;
      // if(this.selectedFile){
      //   const filePath = `departments/${this.newDepartment?.id}`;
      //   const storageRef = this.storage.ref(filePath);
      //   const uploadTask = this.storage.upload(filePath, this.selectedFile);
      //   uploadTask.snapshotChanges().pipe(
      //     finalize(() => {
      //       storageRef.getDownloadURL().subscribe(downloadURL => {
      //         (this.selectedFile as FileUpload).url = downloadURL;
      //         this.departmentImage = downloadURL;
      //         department.departmentImage = downloadURL;
      //         console.log("firebaseUrl: ", department.departmentImage);
      //         this.setuserImageUrl(department, department.id as number);
      //       });
      //     })
      //   ).subscribe();
      // }
      this.toast.showSuccess("Department Created Successfully", "Success");
    })

  }

  onItemSelect($event : ListItem){
    this.selectedSymptoms.push($event.id as number);
    console.log("On symptom select, selectedSymptom: ", this.selectedSymptoms);
  }

  onItemDeSelect($event : ListItem){
    this.selectedSymptoms = this.selectedSymptoms.filter(data => data !== $event.id);
    console.log("On symptom deselect, selectedSymptom: ", this.selectedSymptoms);
  }

  onPageSizeChange() {
    this.page = 1;
    this.getAllDoctors();
  }

  onSortByChange() {
    this.page = 1;
    this.getAllDoctors();
  }

  onSearch() {
    this.page = 1;
    this.getAllDoctors();
  }

  onPageChange(pageNumber: number) {
    this.page = pageNumber ;
    this.getAllDoctors();
  }

}
