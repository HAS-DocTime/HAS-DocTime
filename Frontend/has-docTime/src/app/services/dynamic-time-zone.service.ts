import { DatePipe } from '@angular/common';
import { Injectable } from '@angular/core';
import * as jstz from 'jstimezonedetect';


@Injectable({
  providedIn: 'root'
})
export class DynamicTimeZoneService {
  defaultOffset = -330;

  constructor(private datePipe : DatePipe) { }

  convertToTimeZone(timestamp : number){
    const userTimeZone = jstz.determine().name();
    console.log('User\'s time zone: ' + userTimeZone);
    const convertedTimeStamp = this.datePipe.transform(timestamp, 'yyyy-MM-dd HH:mm:ss', "+0530", userTimeZone);
    console.log(convertedTimeStamp);    
  }
  
}
