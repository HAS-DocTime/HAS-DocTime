import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './components/signup/signup.component';

import { LoginComponent } from './components/login/login.component';
import { AppointmentComponent } from './components/appointment/appointment.component';


const routes: Routes = [
  {path: "", redirectTo: "/register", pathMatch:"full"},
  {path : "register", component : SignupComponent},
  {path : "login", component : LoginComponent},
  {path : "appointment", component : AppointmentComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
