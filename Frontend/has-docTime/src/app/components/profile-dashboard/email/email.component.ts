import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Token } from 'src/app/models/token.model';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent {

  constructor(private authService : AuthService, private userService : UserService) {}

  decodedToken : Token = this.authService.decodeToken();
  userEmail : string = this.decodedToken.sub;
  userRole : string = this.decodedToken.role;

  updateEmailForm : FormGroup = new FormGroup(
    {
      email : new FormControl('', [Validators.email])
    }
  )

  updateEmail(){
    const emailObj = this.updateEmailForm.value;
    this.userService.userObject.subscribe((data)=> {
      emailObj['id'] = data;
      emailObj['role'] = this.userRole;
      this.userService.updateEmail(emailObj).subscribe((data)=> {
        const token : string = data.token;
        sessionStorage.setItem("token", token);
        this.decodedToken  = this.authService.decodeToken();
        this.userEmail = this.decodedToken.sub;
      });
    })
  }
}
