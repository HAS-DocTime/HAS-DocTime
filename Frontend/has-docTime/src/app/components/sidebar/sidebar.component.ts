import { Component, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})


export class SidebarComponent{

  isShowScrollButton = false;
  @Input() isExpanded : boolean = false;
  @Output() toggleSidebar : EventEmitter<boolean> = new EventEmitter<boolean>();

  handleSidebarToggle = () => this.toggleSidebar.emit(!this.isExpanded);

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
