import { AbstractControl, ValidatorFn } from "@angular/forms";

export function validatePassword() : ValidatorFn{
  return (control : AbstractControl) : {[key : string] : any} | null => {
    let enteredPassword = control.value;
    if(/\s/.test(enteredPassword)){
      return {containsWhitespace : true};
    }
    else if(/^[^A-Z]*$/.test(enteredPassword)){
      return {lacksUppercase : true};
    }
    else if(/^[^a-z]*$/.test(enteredPassword)){
      return {lacksLowercase : true}
    }
    else if(/^[^!@#$%^&+=_\-\*/]*$/.test(enteredPassword)){
      return {lacksSpecialChar : true}
    }
    else if(/^[^0-9]*$/.test(enteredPassword)){
      return {lacksNumber : true}
    }
    else if(enteredPassword.length < 8){
      return {minLength : true}
    }
    return null;
  }
}
