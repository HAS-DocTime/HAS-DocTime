import { AbstractControl, ValidatorFn } from "@angular/forms";

export function trimmedInputValidateSpace() : ValidatorFn{
  return (control : AbstractControl) : {[key : string] : any} | null => {
    const textValue : string = control.value;
    const trimmedText = textValue.trim();
    if(/\s/.test(trimmedText)){
      return { containsSpace : true }
    }
    else if(/[^A-Za-z]+/.test(trimmedText)){
      return { pattern : true }
    }
    return null;
  }
}
