import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthenticationService } from '../login/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { User } from '../Models/User';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent {
    users: User[] = [];
    isLoggedIn = false;
    private currentLoggedUser = new User();

    constructor(
      private http: HttpClient,
      private route: ActivatedRoute,
      private router: Router,
      private authenticationService: AuthenticationService) { }

    ngOnInit() {
      // Obtener el objeto de usuario desde sessionStorage
      const currentUsername = this.authenticationService.getLoggedInUsername();
      const currentPassword = this.authenticationService.getLoggedInPassword();
     
      this.isLoggedIn = this.authenticationService.isUserLoggedIn();
      
      if (this.isLoggedIn) {
        this.currentLoggedUser = JSON.parse(this.authenticationService.getLoggedInUser());
        const authHeader = this.authenticationService.getAuthToken();
        const headers = new HttpHeaders({
          'Content-Type':  'application/json',
          'Authorization': authHeader
        });
        this.http.get<User[]>('http://127.0.0.1:8080/user/list', {
          headers: headers,
          responseType: 'json'
        }).subscribe(
          (users) => {
            console.log(users);
            this.users = users;
          },
          (error) => {
            console.error('Error fetching users:', error);
          }
        );
      }else{
        this.router.navigate(['/login']);
      }
    }
}
