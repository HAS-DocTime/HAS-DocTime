import { DatePipe } from '@angular/common';
import { Injectable } from '@angular/core';
import * as jstz from 'jstimezonedetect';
import * as moment from 'moment';
import 'moment-timezone';



@Injectable({
  providedIn: 'root'
})
export class DynamicTimeZoneService {
  defaultOffset = -330;

  constructor(private datePipe : DatePipe) { }

  convertToTimeZone(timestamp : number){
    const userTimeZone = jstz.determine().name();
    console.log('User\'s time zone: ' + userTimeZone);
    const convertedTimeStamp = moment(timestamp).tz(userTimeZone).format('YYYY-MM-DD HH:mm:ss');
    console.log(convertedTimeStamp);    
  }
  
}
