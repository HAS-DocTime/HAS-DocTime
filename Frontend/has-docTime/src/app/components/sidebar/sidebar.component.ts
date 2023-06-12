import { Component, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { Token } from 'src/app/models/token.model';
import { AuthService } from 'src/app/services/auth.service';

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

    const decoded_token : Token = this.authService.decodeToken();

    this.tokenRole = decoded_token.role;
    this.id = parseInt(decoded_token.id);
  }
constructor(private authService : AuthService) { }
  scrollToTop() {
    window.scrollTo({ top: 0 , behavior: 'smooth' });
  }
}
