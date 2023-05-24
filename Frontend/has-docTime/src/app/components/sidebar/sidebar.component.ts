import { Component, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})


export class SidebarComponent implements OnInit{

  isShowScrollButton = false;
  @Input() isExpanded : boolean = false;
  @Output() toggleSidebar : EventEmitter<boolean> = new EventEmitter<boolean>();

  handleSidebarToggle = () => this.toggleSidebar.emit(!this.isExpanded);

  @HostListener("window:scroll", ["$event"])
  onWindowScroll() {
  let pos = (document.documentElement.scrollTop || document.body.scrollTop) + document.documentElement.offsetHeight;
  let max = document.documentElement.scrollHeight;
  let scrollBtn = document.getElementById('scrollBtn');
  if(scrollBtn){

    scrollBtn.style.display = "block";
  }
  if(pos == max || pos==max+1)   {
    if(scrollBtn){
      scrollBtn.style.display = "none";
    }
  }
  }
  tokenRole: string = "";
  id: number = 0;

  ngOnInit(): void {

    const token = sessionStorage.getItem('token');
    if (token) {

      let store = token?.split('.');
      this.tokenRole = atob(store[1]).split(',')[2].split(':')[1];

      this.id = parseInt(atob(store[1]).split(',')[1].split(':')[1].substring(1, this.tokenRole.length - 1));

      this.tokenRole = this.tokenRole.substring(1, this.tokenRole.length - 1);
    }
  }
constructor() { }
  scrollToTop() {
    window.scrollTo({ top: 0 , behavior: 'smooth' });
  }
}
