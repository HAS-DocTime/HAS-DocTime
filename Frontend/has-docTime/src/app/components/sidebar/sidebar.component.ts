import { Component, HostListener, OnInit } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})


export class SidebarComponent{

  isShowScrollButton = false;

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
