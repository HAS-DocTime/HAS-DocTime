import { Component, OnInit } from '@angular/core';
import { PreviousPageUrlServiceService } from 'src/app/services/previous-page-url-service.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{

  constructor(
    private previousPageUrlService : PreviousPageUrlServiceService
  ){}

  ngOnInit(): void {
    this.previousPageUrlService.setPreviousUrl(window.location.pathname);
    const previousUrl = this.previousPageUrlService.getPreviousUrl();
    console.log('Previous URL:', previousUrl);
  }
}
