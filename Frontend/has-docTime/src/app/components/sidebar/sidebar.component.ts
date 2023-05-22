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
    if (token) {
      let store = token?.split('.');
      
      this.tokenRole = atob(store[1]).split(',')[2].split(':')[1];
      this.tokenRole = this.tokenRole.substring(1, this.tokenRole.length-1);
      
    }

  }

  @HostListener("window:scroll", ["$event"])
onWindowScroll() {
let pos = (document.documentElement.scrollTop || document.body.scrollTop) + document.documentElement.offsetHeight;
let max = document.documentElement.scrollHeight;
let scrollBtn = document.getElementById('scrollBtn');
if(scrollBtn){
  scrollBtn.style.display = "block";
}

 if(pos == max )   {
  if(scrollBtn){
    scrollBtn.style.display = "none";
  }
 }
}

  bodyElement = document.getElementsByTagName("body")[0];


  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

}
