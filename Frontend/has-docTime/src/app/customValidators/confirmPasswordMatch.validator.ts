import { AbstractControl, ValidatorFn } from "@angular/forms";

export function confirmPasswordValidator(password: string, confirmPassword: string): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    const passwordField = control.get(password)?.value;
    const confirmPasswordField = control.get(confirmPassword)?.value;
    if (passwordField === confirmPasswordField) {
      control.get('confirmPassword')?.setErrors({passwordMatch : true})
      return { passwordMatch : true };
    }
    control.get('confirmPassword')?.setErrors({passwordMatch : false})
    return {passwordMatch : false}
  };
}
