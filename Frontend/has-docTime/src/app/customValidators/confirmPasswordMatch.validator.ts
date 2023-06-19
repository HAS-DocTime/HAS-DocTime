import { AbstractControl, FormGroup, ValidatorFn } from "@angular/forms";

export function confirmPasswordValidator() : ValidatorFn{
  return (control: AbstractControl): { [key: string]: any } | null => {
    const passwordField = control.get('password') || control.get('newPassword');
    const confirmPasswordField = control.get('confirmPassword');
    if (passwordField?.value !== confirmPasswordField?.value) {
      control.get('confirmPassword')?.setErrors({passwordMismatch : true})

      return { 'passwordMismatch': true };
    }
    else if(confirmPasswordField?.value===""){
      control.get('confirmPassword')?.setErrors({required : true});
      return {'required' : true};
    }
    else if(confirmPasswordField?.value?.match(new RegExp("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&+=\\-_/])(?=\\S+$).{8,}$"))===null){
      control.get('confirmPassword')?.setErrors({pattern : true});
      return {'pattern' : true};
    }
    else{
      control.get('confirmPassword')?.setErrors(null);
      return null;
    }
  }
}
