import { AbstractControl, ValidatorFn } from "@angular/forms";
export function validateDateValidator() : ValidatorFn{
  return (control : AbstractControl) : {[key : string] : any} | null => {
    let givenDate = new Date(control.value);
    let currentDate = new Date();
    if(givenDate.getTime() > currentDate.getTime() || givenDate.getFullYear() + 150 <= currentDate.getFullYear()){
      return {unrealisticDate : true};
    }
    return null;
  }
}
