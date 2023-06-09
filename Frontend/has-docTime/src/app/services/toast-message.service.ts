import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class ToastMessageService {

  constructor(private toast : ToastrService) {}

  show(title: any, message: any){
    this.toast.show(message, title);
  }

  showSuccess(title: any, message: any){
    this.toast.success(message, title);
  }

  showError(title: any, message: any){
    this.toast.error(message, title);
  }

  showWarning(title: any, message: any){
    this.toast.warning(message, title);
  }

  showInfo(title: any, message: any){
    this.toast.info(message, title);
  }
}
