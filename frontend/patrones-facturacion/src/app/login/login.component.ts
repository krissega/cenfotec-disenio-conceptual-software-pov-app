import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from '../login/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: any;
  password : any;
  authenticated: any;
  errorMessage = 'Invalid Credentials';
  successMessage: string ="";
  invalidLogin = false;
  loginSuccess = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService) {   }

    
  ngOnInit() {
  }

  handleLogin() {
  console.log('Username:', this.username);
  console.log('Password:', this.password);
  this.authenticationService.authenticationService(this.username, this.password, this.authenticated).subscribe(
    (result: any) => {
      console.log("result: " + result);
      this.invalidLogin = false;
      this.loginSuccess = true;
      this.successMessage = 'Login Successful.';
      this.router.navigate(['/home']);
    },
    () => {
      this.invalidLogin = true;
      this.loginSuccess = false;
    }
  );      
}
}