import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PreviousPageUrlServiceService {

  private previousUrl: string = "";

  setPreviousUrl(url: string): void {
    this.previousUrl = url;
  }

  getPreviousUrl(): string {
    return this.previousUrl;
  }
}
