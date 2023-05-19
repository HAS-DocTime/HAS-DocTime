import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignupComponent } from './components/signup/signup.component';
import { ReactiveFormsModule } from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http'
import { LoginComponent } from './components/login/login.component';
import { HeaderComponent } from './components/header/header.component';
import { AuthTokenInterceptor } from './interceptors/auth-token-interceptor';
import { AppointmentComponent } from './components/appointment/appointment.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { OurServicesComponent } from './components/our-services/our-services.component';
import { ProfilePageComponent } from './components/profile-page/profile-page.component';
import { CommonModule, DatePipe } from '@angular/common';
import { MedicalHistoryComponent } from './components/medical-history/medical-history.component';
import { DetailedHistoryComponent } from './components/medical-history/detailed-history/detailed-history.component';
import { AppointmentDetailComponent } from './components/appointment-detail/appointment-detail.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { BookAppointmentComponent } from './components/book-appointment/book-appointment.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UserDoctorListComponent } from './components/user-doctor-list/user-doctor-list.component';
import { DepartmentComponent } from './components/department/department.component';
import { DeptDetailComponent } from './components/department/dept-detail/dept-detail.component';
import { SymptomComponent } from './components/symptom/symptom.component';
import { ChronicIllnessComponent } from './components/chronic-illness/chronic-illness.component';
import { ChronicIllnessDetailComponent } from './components/chronic-illness/chronic-illness-detail/chronic-illness-detail.component';
import { SymptomDetailComponent } from './components/symptom/symptom-detail/symptom-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    LoginComponent,
    HeaderComponent,
    AppointmentComponent,
    SidebarComponent,
    HomeComponent,
    OurServicesComponent,
    ProfilePageComponent,
    AppointmentDetailComponent,
    DashboardComponent,
    MedicalHistoryComponent,
    DetailedHistoryComponent,
    BookAppointmentComponent,
    UserDoctorListComponent,
    DepartmentComponent,
    DeptDetailComponent,
    SymptomComponent,
    ChronicIllnessComponent,
    ChronicIllnessDetailComponent,
    SymptomDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatCheckboxModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,
    CommonModule,
    NgbModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass : AuthTokenInterceptor,
      multi: true
    },
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
