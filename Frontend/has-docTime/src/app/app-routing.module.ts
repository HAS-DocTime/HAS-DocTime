import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { AppointmentComponent } from './components/appointment/appointment.component';
import { HomeComponent } from './components/home/home.component';
import { PastAppointmentComponent } from './components/past-appointment/past-appointment.component';
import { DetailedPastAppointmentComponent } from './components/past-appointment/detailed-past-appointment/detailed-past-appointment.component';
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
import { DoctorScheduleAppointmentsComponent } from './components/doctor-schedule-appointments/doctor-schedule-appointments.component';
import { ResolvedCasesArchiveComponent } from './components/resolved-cases-archive/resolved-cases-archive.component';
import { CaseDetailComponent } from './components/case-detail/case-detail.component';
import { PatientAppointmentDetailComponent } from './components/patient-appointment-detail/patient-appointment-detail.component';
import { AppointmentDetailFromDoctorComponent } from './components/patient-appointment-detail/appointment-detail-from-doctor/appointment-detail-from-doctor.component';
import { PastHistoryComponent } from './components/patient-appointment-detail/past-history/past-history.component';
import { OurServicesComponent } from './components/our-services/our-services.component';
import { PatientPastAppointmentByIdComponent } from './components/patient-appointment-detail/past-history/patient-past-appointment-by-id/patient-past-appointment-by-id.component';
import { CreateDepartmentComponent } from './components/department/create-department/create-department.component';


const routes: Routes = [
  {path: "", component : HomeComponent},
  {path : "register", component : SignupComponent},
  {path : "login", component : LoginComponent},
  {path : "dashboard", component : DashboardComponent, children : [
    {path : "appointment", component : AppointmentComponent},
    {path : "doctorScheduleAppointments", component : DoctorScheduleAppointmentsComponent},
    {path : "doctorScheduleAppointments/patientAppointmentDetail", component : PatientAppointmentDetailComponent, children: [
      {path : "appointmentDetails", component : AppointmentDetailFromDoctorComponent},
      {path : "pastHistory", children : [
        {path : "", component : PastHistoryComponent},
        {path : ":id", component : PatientPastAppointmentByIdComponent}
      ]}
    ]},
    {path : "profile", component : ProfilePageComponent},
    {path : "ourServices", component : OurServicesComponent},
    {path : "symptom", component : SymptomComponent},
    {path : "appointment/book", component : BookAppointmentComponent},
    {path : "appointment/:id", component : AppointmentDetailComponent},
    {path : "pastAppointments", component : PastAppointmentComponent},
    {path : "pastAppointments/:id", component : DetailedPastAppointmentComponent},
    {path : "users", component : UserDoctorListComponent},
    {path : "users/:id", component : ProfilePageComponent},
    {path : "doctors", component : UserDoctorListComponent},
    {path : "doctors/:id", component : ProfilePageComponent},
    {path : "departments", component : DepartmentComponent},
    {path : "departments/createDepartment", component : CreateDepartmentComponent},
    {path : "departments/:id", component : DeptDetailComponent},
    {path : "symptoms", component : SymptomComponent},
    {path : "symptoms/:id", component : SymptomDetailComponent},
    {path : "chronicIllness", component : ChronicIllnessComponent},
    {path : "chronicIllness/:id", component : ChronicIllnessDetailComponent},
    {path : "resolvedCasesArchive", component : ResolvedCasesArchiveComponent},
    {path : "resolvedCasesArchive/caseDetail", component : CaseDetailComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
