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


const routes: Routes = [
  {path: "", component : HomeComponent},
  {path : "register", component : SignupComponent},
  {path : "login", component : LoginComponent},
  {path : "dashboard", component : DashboardComponent, children : [
    {path : "", redirectTo : "appointment", pathMatch : 'full'},
    {path : "appointment", component : AppointmentComponent},
    {path : "appointment/:id", component : AppointmentDetailComponent},
    {path : "medicalHistory", component : MedicalHistoryComponent},
    {path : "detailedHistory/:id", component : DetailedHistoryComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
