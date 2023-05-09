import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginComponent } from '../login/login.component';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})


export class SidebarComponent implements OnInit{

  tokenRole: string = "";
  isShowScrollButton = false;

  constructor(private router : Router){}

  ngOnInit(): void {
    const token = sessionStorage.getItem('token');
    console.log(token);
    if (token) {
      let store = token?.split('.');
      console.log(atob(store[1]));
      
      this.tokenRole = atob(store[1]).split(',')[2].split(':')[1];
      this.tokenRole = this.tokenRole.substring(1, this.tokenRole.length-1);
      console.log(this.tokenRole);
      
    }
    // if(this.tokenRole === "PATIENT"){
    //   this.router.navigate(['/dashboard']);
    // }else{
    //   this.router.navigate(['/dashboard/doctorScheduleAppointments']);
    // }
  }

  // @HostListener('window:scroll', [])
  // onWindowScroll() {
  //   if (window.pageYOffset > 300) {
  //     this.isShowScrollButton = true;
  //   } else {
  //     this.isShowScrollButton = false;
  //   }
  // }

  @HostListener("window:scroll", ["$event"])
onWindowScroll() {
//In chrome and some browser scroll is given to body tag
let pos = (document.documentElement.scrollTop || document.body.scrollTop) + document.documentElement.offsetHeight;
let max = document.documentElement.scrollHeight;
let scrollBtn = document.getElementById('scrollBtn');
if(scrollBtn){
  console.log("Block");
  scrollBtn.style.display = "block";
}

 if(pos == max )   {
  if(scrollBtn){
    console.log("None");
    scrollBtn.style.display = "none";
  }
 }
}

  bodyElement = document.getElementsByTagName("body")[0];


  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

}
