import { AbstractControl, ValidatorFn } from "@angular/forms";
import { clippingParents } from "@popperjs/core";
export function validateDateForBookingDoctorValidator() : ValidatorFn{
  return (control : AbstractControl) : {[key : string] : any} | null => {
    let givenDate = new Date(control.value);
    let currentDate = new Date(); 
    let startDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate());
    let endDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate()+7);
    if(!(givenDate >= startDate && givenDate < endDate)){
      return {unavailableDate : true};
    }
    return null;
  }
}
