import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { AppointmentComponent } from './components/appointment/appointment.component';
import { HomeComponent } from './components/home/home.component';
import { MedicalHistoryComponent } from './components/medical-history/medical-history.component';
import { DetailedHistoryComponent } from './components/medical-history/detailed-history/detailed-history.component';
import { AppointmentDetailComponent } from './components/appointment-detail/appointment-detail.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ProfilePageComponent } from './components/profile-page/profile-page.component';
import { BookAppointmentComponent } from './components/book-appointment/book-appointment.component';
import { UserDoctorListComponent } from './components/user-doctor-list/user-doctor-list.component';
import { DepartmentComponent } from './components/department/department.component';
import { DeptDetailComponent } from './components/department/dept-detail/dept-detail.component';
import { SymptomComponent } from './components/symptom/symptom.component';
import { ChronicIllnessComponent } from './components/chronic-illness/chronic-illness.component';
import { ChronicIllnessDetailComponent } from './components/chronic-illness/chronic-illness-detail/chronic-illness-detail.component';
import { SymptomDetailComponent } from './components/symptom/symptom-detail/symptom-detail.component';


const routes: Routes = [
  {path: "", component : HomeComponent},
  {path : "register", component : SignupComponent},
  {path : "login", component : LoginComponent},
  {path : "dashboard", component : DashboardComponent, children : [
    {path : "", redirectTo : "appointment", pathMatch : 'full'},
    {path : "appointment", component : AppointmentComponent},
    {path : "profile", component : ProfilePageComponent},
    {path : "appointment/book", component : BookAppointmentComponent},
    {path : "appointment/:id", component : AppointmentDetailComponent},
    {path : "medicalHistory", component : MedicalHistoryComponent},
    {path : "medicalHistory/:id", component : DetailedHistoryComponent},
    {path : "users", component : UserDoctorListComponent},
    {path : "users/:id", component : ProfilePageComponent},
    {path : "doctors", component : UserDoctorListComponent},
    {path : "doctors/:id", component : ProfilePageComponent},
    {path : "departments", component : DepartmentComponent},
    {path : "departments/:id", component : DeptDetailComponent},
    {path : "symptoms", component : SymptomComponent},
    {path : "symptoms/:id", component : SymptomDetailComponent},
    {path : "chronicIllness", component : ChronicIllnessComponent},
    {path : "chronicIllness/:id", component : ChronicIllnessDetailComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
