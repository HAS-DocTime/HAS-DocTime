import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './components/signup/signup.component';

import { LoginComponent } from './components/login/login.component';
import { AppointmentComponent } from './components/appointment/appointment.component';
import { HomeComponent } from './components/home/home.component';
import { MedicalHistoryComponent } from './components/medical-history/medical-history.component';
import { DetailedHistoryComponent } from './components/medical-history/detailed-history/detailed-history.component';


const routes: Routes = [
  {path: "", component : HomeComponent},
  {path : "register", component : SignupComponent},
  {path : "login", component : LoginComponent},
  {path : "appointment", component : AppointmentComponent},
  {path : "medicalHistory", component : MedicalHistoryComponent},
  {path : "detailedHistory/:id", component : DetailedHistoryComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
