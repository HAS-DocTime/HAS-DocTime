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
import { AuthGuard } from './guards/auth.guard';
import { DashboardGuard } from './guards/dashboard.guard';
import { RoleGuard } from './guards/role.guard';
import { NotFoundComponent } from './components/not-found/not-found.component';


const routes: Routes = [
  {path: "", component : HomeComponent, canActivate: [DashboardGuard]},
  {path : "register", component : SignupComponent, canActivate: [DashboardGuard]},
  {path : "login", component : LoginComponent, canActivate: [DashboardGuard]},
  {path : "dashboard", component : DashboardComponent, children : [
    {path : "appointment", component : AppointmentComponent, canActivate: [RoleGuard], data : {
      roles : ["PATIENT", "DOCTOR", "ADMIN"]
    }},
    {path : "doctorScheduleAppointments", component : DoctorScheduleAppointmentsComponent, canActivate: [RoleGuard], data : {
      roles : ["DOCTOR", "ADMIN"]
    }},
    {path : "doctorScheduleAppointments/patientAppointmentDetail", component : PatientAppointmentDetailComponent, canActivate: [RoleGuard], children: [
      {path : "appointmentDetails", component : AppointmentDetailFromDoctorComponent, canActivate: [RoleGuard], data : {
        roles : ["DOCTOR", "ADMIN"]
      }},
      {path : "pastHistory", canActivate: [RoleGuard], children : [
        {path : "", component : PastHistoryComponent, canActivate: [RoleGuard], data : {roles : ["DOCTOR", "ADMIN"]}},
        {path : ":id", component : PatientPastAppointmentByIdComponent, canActivate: [RoleGuard], data : {roles : ["DOCTOR", "ADMIN"]}}
      ], data : {
        roles : ["DOCTOR", "ADMIN"]
      }}
    ], data : {roles : ["DOCTOR", "ADMIN"]}},
    {path : "profile", component : ProfilePageComponent, canActivate: [RoleGuard], data : {roles : ["PATIENT", "DOCTOR", "ADMIN"]}},
    {path : "ourServices", component : OurServicesComponent, canActivate: [RoleGuard], data : {roles : ["DOCTOR", "ADMIN"]}},
    {path : "appointment/book", component : BookAppointmentComponent, canActivate: [RoleGuard], data : {roles : ["PATIENT", "DOCTOR", "ADMIN"]}},
    {path : "appointment/:id", component : AppointmentDetailComponent, canActivate: [RoleGuard], data : {roles : ["PATIENT", "DOCTOR", "ADMIN"]}},
    {path : "pastAppointments", component : PastAppointmentComponent, canActivate: [RoleGuard], data : {roles : ["PATIENT", "DOCTOR", "ADMIN"]}},
    {path : "pastAppointments/:id", component : DetailedPastAppointmentComponent, canActivate: [RoleGuard], data : {roles : ["PATIENT", "DOCTOR", "ADMIN"]}},
    {path : "users", component : UserDoctorListComponent, canActivate: [RoleGuard], data : {roles : ["ADMIN"]}},
    {path : "users/:id", component : ProfilePageComponent, canActivate: [RoleGuard], data : {roles : ["ADMIN"]}},
    {path : "doctors", component : UserDoctorListComponent, canActivate: [RoleGuard], data : {roles : ["ADMIN"]}},
    {path : "doctors/:id", component : ProfilePageComponent, canActivate: [RoleGuard], data : {roles : ["ADMIN"]}},
    {path : "departments", component : DepartmentComponent, canActivate: [RoleGuard], data : {roles : ["ADMIN"]}},
    {path : "departments/:id", component : DeptDetailComponent, canActivate: [RoleGuard], data : {roles : ["ADMIN"]}},
    {path : "symptoms", component : SymptomComponent, canActivate: [RoleGuard], data : {roles : ["DOCTOR", "ADMIN"]}},
    {path : "symptoms/:id", component : SymptomDetailComponent, canActivate: [RoleGuard], data : {roles : ["DOCTOR", "ADMIN"]}},
    {path : "chronicIllness", component : ChronicIllnessComponent, canActivate: [RoleGuard], data : {roles : ["ADMIN"]}},
    {path : "chronicIllness/:id", component : ChronicIllnessDetailComponent, canActivate: [RoleGuard], data : {roles : ["ADMIN"]}},
    {path : "resolvedCasesArchive", component : ResolvedCasesArchiveComponent, canActivate: [RoleGuard], data : {roles : ["DOCTOR","ADMIN"]}},
    {path : "resolvedCasesArchive/caseDetail", component : CaseDetailComponent, canActivate: [RoleGuard], data : {roles : ["DOCTOR","ADMIN"]}}
  ], canActivate : [AuthGuard]},
  {path : "**", component : NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
