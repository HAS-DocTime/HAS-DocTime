import { AbstractControl, FormGroup, ValidatorFn } from "@angular/forms";

export function confirmPasswordValidator() : ValidatorFn{
  return (control: AbstractControl): { [key: string]: any } | null => {
    const passwordField = control.get('password');
    const confirmPasswordField = control.get('confirmPassword');
    if (passwordField?.value !== confirmPasswordField?.value) {
      control.get('confirmPassword')?.setErrors({passwordMismatch : true})
      return { 'passwordMismatch': true };
    }
    else if(confirmPasswordField?.value===""){
      control.get('confirmPassword')?.setErrors({required : true});
      return {'required' : true};
    }
    else{
      control.get('confirmPassword')?.setErrors(null);
      return null;
    }
  }
}
