import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';
import { DatePipe } from '@angular/common';
import { Doctor } from 'src/app/models/doctor.model';
import { DoctorService } from 'src/app/services/doctor.service';
import { AdminService } from 'src/app/services/admin.service';
import { Admin } from 'src/app/models/admin.model';
import { ActivatedRoute, Router } from '@angular/router';
import { validateDateValidator } from 'src/app/customValidators/validateDate.validator';
import { trimmedInputValidateSpace } from 'src/app/customValidators/trimmedInputValidateSpace.validator';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css'],
})
export class ProfilePageComponent implements OnInit, OnDestroy {
  constructor(
    private datePipe: DatePipe,
    private userService: UserService,
    private cdr: ChangeDetectorRef,
    private doctorService: DoctorService,
    private adminService: AdminService,
    private route: ActivatedRoute,
    private router: Router
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

  ngOnInit(): void {
    this.route.url.subscribe((data) => {
      this.urlPath = data[0].path;
      const token = sessionStorage.getItem('token');
      if (token) {
        let store = token?.split('.');
        this.tokenRole = atob(store[1]).split(',')[2].split(':')[1];
        this.id = parseInt(
          atob(store[1])
            .split(',')[1]
            .split(':')[1]
            .substring(1, this.tokenRole.length - 1)
        );

        this.tokenRole = this.tokenRole.substring(1, this.tokenRole.length - 1);

        if (this.tokenRole === 'ADMIN') {
          if (data[0].path === 'users') {
            this.route.params.subscribe((data) => {
              this.id = parseInt(data['id']);
              this.adminService.getSingleUser(this.id).subscribe((data) => {
                this.user = data;
                this.id = data.id as number;
                const nameArray: string[] =
                  this.user?.name?.split(' ', 2) ?? [];
                this.firstName = nameArray[0];
                this.lastName = nameArray[1];
                if (data.dob) {
                  this.dateFromAPI = new Date(data?.dob);
                }

                this.formattedDate = this.datePipe.transform(
                  this.dateFromAPI,
                  'yyyy-MM-dd'
                );

                this.editForm.patchValue({
                  firstName: this.firstName,
                  lastName: this.lastName,
                  email: this.user.email,
                  gender: this.user.gender,
                  bloodGroup: this.user.bloodGroup,
                  contact: this.user.contact,
                  height: this.user.height,
                  weight: this.user.weight,
                  dob: this.formattedDate,
                });
              });
            });
          } else if (data[0].path === 'doctors') {
            this.route.params.subscribe((data) => {
              this.id = parseInt(data['id']);
              this.doctorService.getDoctor(this.id).subscribe((data) => {
                this.doctor = data;
                this.user = data.user;
                this.id = data.id as number;
                const docNameArray: string[] =
                  this.doctor?.user?.name?.split(' ', 2) ?? [];
                this.firstName = docNameArray[0];
                this.lastName = docNameArray[1];
                if (data.user.dob) {
                  this.dateFromAPI = new Date(data.user.dob);
                }
                this.formattedDate = this.datePipe.transform(
                  this.dateFromAPI,
                  'yyyy-MM-dd'
                );

                this.editForm.patchValue({
                  firstName: this.firstName,
                  lastName: this.lastName,
                  email: this.doctor.user.email,
                  gender: this.doctor.user.gender,
                  bloodGroup: this.doctor.user.bloodGroup,
                  contact: this.doctor.user.contact,
                  height: this.doctor.user.height,
                  weight: this.doctor.user.weight,
                  dob: this.formattedDate,
                  qualification: this.doctor.qualification,
                  casesSolved: this.doctor.casesSolved,
                });
              });
            });
          } else {
            this.adminService.getAdmin(this.id).subscribe((data) => {
              this.admin = data;
              this.user = data.user;
              this.id = data.id as number;
              const adminNameArray: string[] =
                this.admin?.user?.name?.split(' ', 2) ?? [];
              this.firstName = adminNameArray[0];
              this.lastName = adminNameArray[1];
              if (data?.user?.dob) {
                this.dateFromAPI = new Date(data?.user?.dob);
              }

              this.formattedDate = this.datePipe.transform(
                this.dateFromAPI,
                'yyyy-MM-dd'
              );

              this.editForm.patchValue({
                firstName: this.firstName,
                lastName: this.lastName,
                email: this.admin?.user?.email,
                gender: this.admin?.user?.gender,
                bloodGroup: this.admin?.user?.bloodGroup,
                contact: this.admin?.user?.contact,
                height: this.admin?.user?.height,
                weight: this.admin?.user?.weight,
                dob: this.formattedDate,
              });
            });
          }
        } else if (this.tokenRole === 'DOCTOR') {
          this.doctorService.getDoctor(this.id).subscribe((data) => {
            this.doctor = data;
            this.user = data.user;
            this.id = data.id as number;
            const docNameArray: string[] =
              this.doctor?.user?.name?.split(' ', 2) ?? [];
            this.firstName = docNameArray[0];
            this.lastName = docNameArray[1];
            if (data.user.dob) {
              this.dateFromAPI = new Date(data.user.dob);
            }
            this.formattedDate = this.datePipe.transform(
              this.dateFromAPI,
              'yyyy-MM-dd'
            );

            this.editForm.patchValue({
              firstName: this.firstName,
              lastName: this.lastName,
              email: this.doctor.user.email,
              gender: this.doctor.user.gender,
              bloodGroup: this.doctor.user.bloodGroup,
              contact: this.doctor.user.contact,
              height: this.doctor.user.height,
              weight: this.doctor.user.weight,
              dob: this.formattedDate,
              qualification: this.doctor.qualification,
              casesSolved: this.doctor.casesSolved,
            });
          });
        } else {
          this.userService.getUser(this.id).subscribe((data) => {
            this.user = data;
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

            this.editForm.patchValue({
              firstName: this.firstName,
              lastName: this.lastName,
              email: this.user.email,
              gender: this.user.gender,
              bloodGroup: this.user.bloodGroup,
              contact: this.user.contact,
              height: this.user.height,
              weight: this.user.weight,
              dob: this.formattedDate,
            });
          });
        }
      }
    });
  }

  editForm: FormGroup = new FormGroup({
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
    contact: new FormControl({ value: '', disabled: this.disable }, [
      Validators.required,
    ]),
    height: new FormControl({ value: '', disabled: this.disable }),
    weight: new FormControl({ value: '', disabled: this.disable }),
    email: new FormControl({ value: '', disabled: this.disable }, [
      Validators.required,
    ]),
    qualification: new FormControl({ value: '', disabled: this.disable }),
    casesSolved: new FormControl({ value: '', disabled: true }),
    patientChronicIllness: new FormArray([]),
  });

  ngOnDestroy(): void {}

  onSubmit() {
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

    if (this.tokenRole === 'PATIENT' || this.urlPath === 'users') {
      user.role = this.user?.role;
      user.email = this.user?.email;

      this.userService.updateUser(user, this.id).subscribe((data) => {
        this.toggleDisable();
        this.cdr.detectChanges();

        this.userService.getUser(this.id).subscribe((data) => {
          this.user = data;
        });
      });
    } else if (this.tokenRole === 'DOCTOR' || this.urlPath === 'doctors') {
      user.id = this.doctor?.user?.id;
      user.role = this.doctor?.user.role;
      user.email = this.doctor?.user.email;
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
        });
        this.toggleDisable();
      });
    } else if (
      this.tokenRole === 'ADMIN' &&
      this.urlPath !== 'users' &&
      this.urlPath !== 'doctors'
    ) {
      let userId = 0;
      user.role = this.admin?.user?.role;
      user.email = this.admin?.user?.email;
      let admin: Admin = {
        user: user,
      };
      this.userService.updateAdmin(admin, this.id).subscribe((data) => {
        this.adminService.getAdmin(this.id).subscribe((data) => {
          this.admin = data;
          this.user = data.user;
          this.cdr.detectChanges();
        });
        this.toggleDisable();
      });
    }
  }

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
      });
    }
  }

  get maxDate(): string {
    return new Date().toISOString().split('T')[0];
  }
  get minDate(): string {
    const date = new Date();
    date.setFullYear(date.getFullYear()-150);
    return date.toISOString().split('T')[0];
  }
}
