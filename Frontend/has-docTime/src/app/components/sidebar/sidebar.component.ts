import { Component, HostListener } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {

  isShowScrollButton = false;

  @HostListener('window:scroll', [])
  onWindowScroll() {
    if (window.pageYOffset > 300) {
      this.isShowScrollButton = true;
    } else {
      this.isShowScrollButton = false;
    }
  }

  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

}
