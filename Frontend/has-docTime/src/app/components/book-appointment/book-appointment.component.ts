import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { validateDateForBookingDoctorValidator } from 'src/app/customValidators/validateDateForBookingDoctor.validator';
import { Doctor } from 'src/app/models/doctor.model';
import { Symptom } from 'src/app/models/symptom.model';
import { TimeSlot } from 'src/app/models/timeSlot.model';
import { User } from 'src/app/models/user.model';
import { AppointmentService } from 'src/app/services/appointment.service';
import { DoctorService } from 'src/app/services/doctor.service';
import { SymptomService } from 'src/app/services/symptom.service';
import { ToastMessageService } from 'src/app/services/toast-message.service';
import { UserService } from 'src/app/services/user.service';

interface SortByOption {
  label: string;
  value: string;
}

@Component({
  selector: 'app-book-appointment',
  templateUrl: './book-appointment.component.html',
  styleUrls: ['./book-appointment.component.css']
})
export class BookAppointmentComponent implements OnInit{

  symptoms : Symptom[] = [];
  selectedSymptom : number[] = [];
  currentUser : User = {
  };
  startTime? : Date;
  endTime? : Date;
  date! : Date;
  startTimeInString : string = "";
  endTimeInString : string = "";
  tokenRole! : string;
  id! : number;
  doctorList : Doctor[] = [];
  noDataFound : boolean = false;
  noDataFoundImg : string = "https://firebasestorage.googleapis.com/v0/b/ng-hasdoctime-images.appspot.com/o/dataNotFound.png?alt=media&token=2533f507-7433-4a70-989d-ba861273e537";
  currentDoctor : Doctor = {

    user : {
      name : "Dummy Name"
      },
      qualification : "Dummy Qualification",
      casesSolved : 0,
      available : true,
      department  : {
      }
  };
  selectedSymptoms : string[] = [];
  datePicker = document.getElementById("datePicker");
  page = 1;
  totalPages = 0;
  size = 5;
  sortBy = 'user.name';
  search = '';
  availableTimeSlots : TimeSlot[] = [];



  constructor(private symptomService : SymptomService, private appointmentService : AppointmentService,
     private userService : UserService, private router : Router, private route : ActivatedRoute, private doctorService : DoctorService, private location : Location, private toast: ToastMessageService){}



  ngOnInit(){
    const token = sessionStorage.getItem('token');
    if (token) {

      let store = token?.split('.');
      this.tokenRole = atob(store[1]).split(',')[2].split(':')[1];

      this.id = parseInt(atob(store[1]).split(',')[1].split(':')[1].substring(1, this.tokenRole.length - 1));

      this.tokenRole = this.tokenRole.substring(1, this.tokenRole.length - 1);
    }
    this.symptomService.getSymptomsList().subscribe((data)=> {
      this.symptoms = data;
    })

    this.bookAppointment.controls['symptoms'].valueChanges.subscribe(data=> {
      this.selectedSymptom = [];
      this.selectedSymptoms = [];
      for(let symptomName of data){
        if(symptomName['id']!=='')
        this.selectedSymptom.push(parseInt(symptomName['id']));
        for(let symptom of this.symptoms){
          if(parseInt(symptomName['id'])===symptom.id){
            this.selectedSymptoms.push(symptom.name as string);
            break;
          }
        }
      }

      let dateInput = document.getElementById("date");
      let timeSlotInput = document.getElementById("timeSlot");
      dateInput?.addEventListener("change", ()=> {
        (timeSlotInput as HTMLInputElement).value = "";
        (timeSlotInput as HTMLSelectElement).selectedIndex = 0;
      })
    })

  }

  sizeOptions = [5, 10, 15];
  range(totalPages: number): number[] {
    return Array(totalPages).fill(0).map((_, index) => index + 1);
  }

  sortByOptions: SortByOption[] = [
    { label: 'StartTime', value: 'timeSlotForAppointment.startTime' },
    { label: 'Doctor Name', value: 'doctor.user.name' }
  ];
  params : any = {};



  getDoctorsBySymptomAndTimeSlot(){
    this.startTime = this.bookAppointment.value["timeSlot"].split("-")[0];
    this.endTime = this.bookAppointment.value["timeSlot"].split("-")[1];
    this.date = this.bookAppointment.value["date"];
    this.startTimeInString = `${this.date}T${this.startTime}`
    this.endTimeInString = `${this.date}T${this.endTime}`
    this.bookAppointment.value["timeSlotStartTime"] = this.startTimeInString;
    this.bookAppointment.value["timeSlotEndTime"] = this.endTimeInString;
    this.doctorService.getDoctorsBySymptomAndTimeSlot(this.bookAppointment.value, this.params).subscribe((data)=> {
      console.log(data);
      this.doctorList = data.content as Doctor[];
      if(this.doctorList.length<=0){
        this.noDataFound = true;
      }
      else{
        this.noDataFound = false;
      }
    });

  }

  bookAppointment : FormGroup = new FormGroup({
    symptoms : new FormArray([
      new FormGroup({
          id : new FormControl('', Validators.required),

      }),
    ]),
    timeSlot : new FormControl('', Validators.required),
    description : new FormControl(""),
    date : new FormControl(new Date().toISOString().split("T")[0], [Validators.required, validateDateForBookingDoctorValidator()])
  })

  confirmAppointment : FormGroup = new FormGroup({
    timeSlot : new FormControl('', Validators.required)
  })

  createAppointment(){
    for(let symptom of this.symptomList.value){
      symptom.id = parseInt(symptom.id);
    }
    this.bookAppointment.value["user"] = {
      "id": this.id
    }
    this.bookAppointment.value["doctor"] = {
      "id": this.currentDoctor.id
    }
    //Hard-Coded as of now
    this.bookAppointment.value["timeSlotForAppointment"] = {
      "id": this.confirmAppointment.get('timeSlot')?.value
    }
    console.log(this.bookAppointment.value);
    this.closeModal();
    this.appointmentService.createAppointment(this.bookAppointment.value).subscribe((data)=> {
      this.router.navigate(["../"], {relativeTo : this.route});
      this.toast.showSuccess(`Appointemnt created successfully`, "Created!");
    })
  }

  closeModal() {
    var modal = document.querySelectorAll(".modal-backdrop");
    modal.forEach((mod)=> {
      (mod as HTMLDivElement).style.display = "none";
    })
    document.body.style.overflow = "auto";
  }

  get symptomList() : FormArray{
    return this.bookAppointment.get('symptoms') as FormArray
  }

  addSymptom(){
    this.symptomList.push(new FormGroup({
      id : new FormControl('', Validators.required)
    }))
  }

  deleteSymptom(id : number){
    this.symptomList.removeAt(id);
  }

  backToAppointments(){
    this.router.navigate(["../"], {relativeTo : this.route})
  }

  navigateBack(){
    this.location.back();
  }

  selectDoctor(index : number){
    this.currentDoctor = this.doctorList[index];
    this.currentDoctor.availableTimeSlots = this.currentDoctor.availableTimeSlots?.filter((timeSlot)=>{
      if((new Date()).getTime() < (timeSlot.startTime as number)){
        return true;
      }
      return false;
    })

  }

  convertTimeStampToDate(timestamp : number | undefined){
    return new Date(timestamp as number);
  }

  getMinDate(){
    var today = new Date();
    return today.toISOString().split('T')[0];
  }

  getMaxDate(){
    var today = new Date();
    today.setDate(today.getDate()+6);
    return today.toISOString().split('T')[0];
  }

  checkValidTimeSlot(time : string){
    let date = this.bookAppointment.get('date')?.value;
    date+=("T" + time);
    const currentDate = new Date();
    date = new Date(date);
    if(date<currentDate){
      return true;
    }
    return false;
  }

  getData(){
    // Add query parameters based on selected options
    if (this.size) {
      this.params.size = this.size;
    }
    if (this.sortBy) {
      this.params.sortBy = this.sortBy;
    }
    if (this.search) {
      this.params.search = this.search;
    }
    this.params.page = this.page-1;

    this.startTime = this.bookAppointment.value["timeSlot"].split("-")[0];
    this.endTime = this.bookAppointment.value["timeSlot"].split("-")[1];
    this.date = this.bookAppointment.value["date"];
    this.startTimeInString = `${this.date}T${this.startTime}`
    this.endTimeInString = `${this.date}T${this.endTime}`
    this.bookAppointment.value["timeSlotStartTime"] = this.startTimeInString;
    this.bookAppointment.value["timeSlotEndTime"] = this.endTimeInString;
    this.doctorService.getDoctorsBySymptomAndTimeSlot(this.bookAppointment.value, this.params).subscribe((data)=> {
      console.log(data);
      this.doctorList = data.content as Doctor[];
      if(this.doctorList.length<=0){
        this.noDataFound = true;
      }
      else{
        this.noDataFound = false;
      }
    });
  }

  onPageSizeChange() {
    this.page = 1;
    this.getData();
  }

  onSortByChange() {
    this.page = 1;
    this.getData();
  }

  onSearch() {
    this.page = 1;
    this.getData();
  }

  onPageChange(pageNumber: number) {
    this.page = pageNumber ;
    this.getData();
  }




}
