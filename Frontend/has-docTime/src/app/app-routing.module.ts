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
import { DoctorScheduleAppointmentsComponent } from './components/doctor-schedule-appointments/doctor-schedule-appointments.component';
import { ResolvedCasesArchiveComponent } from './components/resolved-cases-archive/resolved-cases-archive.component';


const routes: Routes = [
  {path: "", component : HomeComponent},
  {path : "register", component : SignupComponent},
  {path : "login", component : LoginComponent},
  {path : "dashboard", component : DashboardComponent, children : [
    {path : "appointment", component : AppointmentComponent},
    {path : "doctorScheduleAppointments", component : DoctorScheduleAppointmentsComponent},
    {path : "profile", component : ProfilePageComponent},
    {path : "appointment/book", component : BookAppointmentComponent},
    {path : "appointment/:id", component : AppointmentDetailComponent},
    {path : "medicalHistory", component : MedicalHistoryComponent},
    {path : "resolvedCasesArchive", component : ResolvedCasesArchiveComponent},
    {path : "medicalHistory/:id", component : DetailedHistoryComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
