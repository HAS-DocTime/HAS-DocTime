import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';
import { DatePipe, Location } from '@angular/common';
import { Doctor } from 'src/app/models/doctor.model';
import { DoctorService } from 'src/app/services/doctor.service';
import { AdminService } from 'src/app/services/admin.service';
import { Admin } from 'src/app/models/admin.model';
import { ActivatedRoute, Router } from '@angular/router';
import { validateDateValidator } from 'src/app/customValidators/validateDate.validator';
import { trimmedInputValidateSpace } from 'src/app/customValidators/trimmedInputValidateSpace.validator';
import { CountryService } from 'src/app/services/country.service';
import { Country } from 'src/app/models/country.model';
import { FileUpload } from 'src/app/models/fileUpload.model';
import { ToastMessageService } from 'src/app/services/toast-message.service';
import { AngularFireStorage } from '@angular/fire/compat/storage';
import { finalize } from 'rxjs';
import { DepartmentService } from 'src/app/services/department.service';
import { Token } from 'src/app/models/token.model';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css'],
})
export class ProfilePageComponent implements OnInit {
  constructor(
    private datePipe: DatePipe,
    private userService: UserService,
    private cdr: ChangeDetectorRef,
    private doctorService: DoctorService,
    private adminService: AdminService,
    private route: ActivatedRoute,
    private router: Router,
    private location : Location,
    private countryService : CountryService,
    private toast : ToastMessageService,
    private storage: AngularFireStorage,
    private departmentService : DepartmentService,
    private authService : AuthService
  ) {}

  user?: User;
  doctor?: Doctor;
  admin?: Admin;
  disable: boolean = true;
  firstName: string = '';
  lastName: string = '';
  id: number = 0;
  tokenRole: string = '';
  dateFromAPI = new Date('2022-05-12T00:00:00');
  formattedDate = this.datePipe.transform(this.dateFromAPI, 'yyyy-MM-dd');
  urlPath!: string;
  doctors!: Doctor[];
  countries : Country[] = [];
  selectedFile: FileUpload | null = null;
  imageUrl: string = "";
  isLoading: boolean = false;
  currentUrl! : any;

  ngOnInit(): void {

    this.currentUrl = this.router.url;
    
    
    
      const decoded_token : Token = this.authService.decodeToken();

    this.tokenRole = decoded_token.role;
    this.id = parseInt(decoded_token.id);

        if (this.tokenRole === 'ADMIN') {
          if (this.currentUrl.includes('/users')) {
              this.urlPath = "users";
              const id = this.route.parent?.snapshot.paramMap.get('id');
              this.id = parseInt(id as string);
              this.getUser(this.id);
          } else if (this.currentUrl.includes('/doctors')) {
            this.urlPath = "doctors";
              const id = this.route.parent?.snapshot.paramMap.get('id');
              this.id = parseInt(id as string);
              this.getDoctor(this.id);
          } else {
            this.getAdmin(this.id);
          }
        } else if (this.tokenRole === 'DOCTOR') {
          this.getDoctor(this.id)
        } else {
          this.getUser(this.id);
        }

  }

  editForm: FormGroup = new FormGroup({
    imageUrl: new FormControl({ value: '', disabled: this.disable }),

    firstName: new FormControl({ value: '', disabled: this.disable }, [
      Validators.required, trimmedInputValidateSpace()
    ]),
    lastName: new FormControl({ value: '', disabled: this.disable }, [
      Validators.required, trimmedInputValidateSpace()
    ]),
    dob: new FormControl({ value: '', disabled: this.disable }, [
      Validators.required, validateDateValidator()
    ]),
    gender: new FormControl({ value: '', disabled: this.disable }, [
      Validators.required,
    ]),
    bloodGroup: new FormControl({ value: '', disabled: this.disable }, [
      Validators.required,
    ]),
    contact: new FormGroup({
      countryCode: new FormControl({ value: '', disabled: this.disable}, [Validators.required]),
      number: new FormControl({ value: '', disabled: this.disable}, [Validators.required])
    }),
    height: new FormControl({ value: '', disabled: this.disable }),
    weight: new FormControl({ value: '', disabled: this.disable }),
    email: new FormControl({ value: '', disabled: this.disable }, [
      Validators.required,
    ]),
    qualification: new FormControl({ value: '', disabled: this.disable }),
    department: new FormControl({ value: '', disabled: this.disable }),
    casesSolved: new FormControl({ value: '', disabled: true }),
    patientChronicIllness: new FormArray([]),
  });

  private getAdmin(id: number) {
    this.adminService.getAdmin(this.id).subscribe((data) => {
      this.admin = data;
      this.user = data.user;
      this.imageUrl = this.user?.imageUrl as string;
      
      this.id = data.id as number;
      const adminNameArray: string[] = this.admin?.user?.name?.split(' ', 2) ?? [];
      this.firstName = adminNameArray[0];
      this.lastName = adminNameArray[1];
      if (data?.user?.dob) {
        this.dateFromAPI = new Date(data?.user?.dob);
      }

      this.formattedDate = this.datePipe.transform(
        this.dateFromAPI,
        'yyyy-MM-dd'
      );
      this.countryCodeDropdownMethod();
      this.editFormPatchValue();
    });
  }

  private getDoctor(id: number) {
    this.doctorService.getDoctor(id).subscribe((data) => {
      this.doctor = data;
      this.user = data.user;
      this.imageUrl = this.user.imageUrl as string;
      this.id = data.id as number;
      const docNameArray: string[] = this.doctor?.user?.name?.split(' ', 2) ?? [];
      this.firstName = docNameArray[0];
      this.lastName = docNameArray[1];
      if (data.user.dob) {
        this.dateFromAPI = new Date(data.user.dob);
      }
      this.formattedDate = this.datePipe.transform(
        this.dateFromAPI,
        'yyyy-MM-dd'
      );
      this.departmentDropDownMethod();
      this.countryCodeDropdownMethod();
      this.editFormPatchValue();
    });
  }

  private getUser(id: number) {
    this.userService.getUser(id).subscribe((data) => {
      this.user = data;
      this.imageUrl = this.user.imageUrl as string;
      this.id = data.id as number;
      const nameArray: string[] = this.user?.name?.split(' ', 2) ?? [];
      this.firstName = nameArray[0];
      this.lastName = nameArray[1];
      if (data.dob) {
        this.dateFromAPI = new Date(data?.dob);
      }

      this.formattedDate = this.datePipe.transform(
        this.dateFromAPI,
        'yyyy-MM-dd'
      );
      this.countryCodeDropdownMethod();
      this.editFormPatchValue();
    });
  }

  private editFormPatchValue() {
    this.editForm.patchValue({
      firstName: this.firstName,
      lastName: this.lastName,
      email: this.user?.email,
      gender: this.user?.gender,
      bloodGroup: this.user?.bloodGroup,
      contact: {
        number: this.user?.contact?.split("-")[1]
      },
      height: this.user?.height,
      weight: this.user?.weight,
      dob: this.formattedDate,
      qualification: this.doctor?.qualification,
      casesSolved: this.doctor?.casesSolved,
    });
  }

  private countryCodeDropdownMethod() {
    this.countries = [];
    this.countryService.getAllCountries().then((data) => {
      this.countries = data;
      var dropdown = document.getElementById('countryCodeDropdown');
      this.countries.forEach(country => {
        if (country.code === this.user?.contact?.split("-")[0]) {
          var option = document.createElement('option');
          option.value = country.code;
          option.textContent = country.code + ":" + country.name;
          option.selected = true;
          dropdown?.prepend(option);
          this.editForm.patchValue({
            contact : {
              countryCode: country.code,
            }
          });
        } else {
          var option = document.createElement('option');
          option.value = country.code;
          option.textContent = country.code + ":" + country.name;
          dropdown?.appendChild(option);
        }

      });
    });
  }

  private departmentDropDownMethod(){
    this.departmentService.getDepartmentsWithoutPagination().subscribe(data => {
      var dropdown = document.getElementById('departmentDropDown');
      data.forEach(department => {
        if(department.id === this.doctor?.department.id){
          var option = document.createElement('option');
          option.value = department.id?.toString() as string;
          option.textContent = department.name as string;
          option.selected = true;
          dropdown?.prepend(option);
          this.editForm.patchValue({
            department : department.id,
          });
        }else{
          var option = document.createElement('option');
          option.value = department.id?.toString() as string;
          option.textContent = department.name as string;
          dropdown?.appendChild(option);
        }
      });
    });
  }
  submitProfile() {
    const date = new Date();
    this.editForm.value['firstName'] = this.editForm.value["firstName"].trim();
    this.editForm.value['lastName'] = this.editForm.value["lastName"].trim();
    const user = this.editForm.value;
    if (date.getFullYear() > new Date(user.dob as Date).getFullYear()) {
      let age =
        date.getFullYear() - new Date(user.dob as Date).getFullYear() - 1;
      if (date.getMonth() > new Date(user.dob as Date).getMonth()) {
        age++;
      } else if (date.getMonth() === new Date(user.dob as Date).getMonth()) {
        if (date.getDate() >= new Date(user.dob as Date).getDate()) {
          age++;
        }
      }
      user.age = age;
    }
    user.contact = this.editForm.get('contact')?.get('countryCode')?.value + "-" + this.editForm.get('contact')?.get('number')?.value;
    user.name = user.firstName + ' ' + user.lastName;
    let chronicIllnesses = [];
    for (let i = 0; i < user.patientChronicIllness.length; i++) {
      let chronicIllness = {
        chronicIllness: {
          id: parseInt(user.patientChronicIllness[i].name),
        },
        yearsOfIllness: user.patientChronicIllness[i].yearsOfIllness,
      };
      chronicIllnesses.push(chronicIllness);
    }
    user.patientChronicIllness = chronicIllnesses;

    if (this.tokenRole === 'PATIENT' || this.currentUrl.includes('/users')) {
      user.role = this.user?.role;
      user.email = this.user?.email;
      user.imageUrl = this.imageUrl;
      this.userService.updateProfileImage.next(user.imageUrl);
      this.userService.updateUser(user, this.id).subscribe((data) => {
        this.toggleDisable();
        this.cdr.detectChanges();

        this.userService.getUser(this.id).subscribe((data) => {
          this.user = data;
          this.isLoading = false;
        });
        this.toast.showSuccess("Patient Updated Successfully", "Success");
      }, (err)=> {
        if(err){
          this.toast.showError("Unexpected Error Occurred", "Error");
        }
      });
    } else if (this.tokenRole === 'DOCTOR' || this.currentUrl.includes('/doctors')) {
      user.id = this.doctor?.user?.id;
      user.role = this.doctor?.user.role;
      user.email = this.doctor?.user.email;
      user.imageUrl = this.imageUrl;
      this.userService.updateProfileImage.next(user.imageUrl);
      let doctor: Doctor = {
        user: user,
        qualification: this.editForm.value.qualification,
        casesSolved: this.editForm.value.casesSolved,
        available: true,
        department: {
          id: 1,
        },
      };

      this.userService.updateDoctor(doctor, this.id).subscribe((data) => {
        this.doctorService.getDoctor(this.id).subscribe((data) => {
          this.doctor = data;
          this.user = data.user;
          this.cdr.detectChanges();
          this.isLoading = false;
        });
        this.toggleDisable();
        this.toast.showSuccess("Doctor Updated Successfully",  "Success");
      }, (err)=> {
        if(err){
          this.toast.showError("Unexpected Error Occurred", "Error");
        }
      });
    } else if (
      this.tokenRole === 'ADMIN' &&
      this.urlPath !== 'users' &&
      this.urlPath !== 'doctors'
    ) {
      let userId = 0;
      user.role = this.admin?.user?.role;
      user.email = this.admin?.user?.email;
      user.imageUrl = this.imageUrl;
      this.userService.updateProfileImage.next(user.imageUrl);
      let admin: Admin = {
        user: user,
      };
      this.adminService.updateAdmin(admin, this.id).subscribe((data) => {
        this.adminService.getAdmin(this.id).subscribe((data) => {
          this.admin = data;
          this.user = data.user;
          this.cdr.detectChanges();
          this.isLoading != this.isLoading;
        });
        this.toggleDisable();
        this.toast.showSuccess("Admin Updated Successfully", "Success");
      }, (err)=> {
        if(err){
          this.toast.showError("Unexpected Error Occurred", "Error");
        }
      });
    }
  }

  onSubmit() {
    this.isLoading = true;
    if (this.selectedFile != null) {
      const filePath = `profiles/${this.user?.id}`;
      const storageRef = this.storage.ref(filePath);
      const uploadTask = this.storage.upload(filePath, this.selectedFile);
      uploadTask.snapshotChanges().pipe(
        finalize(() => {
          storageRef.getDownloadURL().subscribe(downloadURL => {
            (this.selectedFile as FileUpload).url = downloadURL;
            this.imageUrl = downloadURL;
            this.user!.imageUrl = downloadURL;
            this.userService.updateProfileImage.next(this.user?.imageUrl as string);
            this.submitProfile();
          });
        })
      ).subscribe();
    }
    else {
      this.submitProfile();
    }
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];

    const reader = new FileReader();
    reader.onload = (e: any) => {
      this.imageUrl = e.target.result;
    };
    if (this.selectedFile) {
      reader.readAsDataURL(event.target.files[0]);
    }
  }

  // uploadProfilePicture(){

  //   if(this.selectedFile){
  //     const filePath = `profiles/${this.selectedFile.name}`;
  //     const storageRef = this.storage.ref(filePath);
  //     const uploadTask = this.storage.upload(filePath, this.selectedFile);

  //     uploadTask.snapshotChanges().pipe(
  //       finalize(() => {
  //         storageRef.getDownloadURL().subscribe(downloadURL => {
  //           (this.selectedFile as FileUpload).url = downloadURL;
  //           this.imageUrl = downloadURL;
  //         });
  //       })
  //     ).subscribe();
  //   }
  // }

  toggleDisable() {
    this.disable = !this.disable;
    // update the disable property of each form field
    Object.keys(this.editForm.controls).forEach((key) => {
      if (this.disable) {
        this.editForm.controls[key].disable();
      } else {
        if (key !== 'casesSolved' && key !== 'email') {
          this.editForm.controls[key].enable();
        }
      }
    });
  }
  delete() {
    if (this.urlPath === 'users') {
      this.adminService.deleteUser(this.id).subscribe((data) => {
        this.router.navigate(['../'], { relativeTo: this.route });
        this.toast.show(`Patient with ID ${this.id} deleted`, "Patient Deleted");
      });
    }
  }

  navigateBack(){
    this.location.back();
  }

  get maxDate(): string {
    return new Date().toISOString().split('T')[0];
  }
  get minDate(): string {
    const date = new Date();
    date.setFullYear(date.getFullYear() - 150);
    return date.toISOString().split('T')[0];
  }
}
