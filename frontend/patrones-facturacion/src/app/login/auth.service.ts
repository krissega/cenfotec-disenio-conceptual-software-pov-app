import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { User } from '../Models/User';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  // BASE_PATH: 'http://localhost:8081'
  USER_NAME_SESSION_ATTRIBUTE_NAME     = 'authenticatedUser'
  USER_NAME_SESSION_ATTRIBUTE_USERNAME = 'username'
  USER_NAME_SESSION_ATTRIBUTE_PASSWORD = 'password'
  USER_NAME_SESSION_ATTRIBUTE_AUTHENTICATED = 'authenticatedstatus'
  USER_NAME_SESSION_ATTRIBUTE_USER = 'loggedUser';
  USER_NAME_SESSION_ATTRIBUTE_AUTH_TOKEN = "authToken";

  public userName : any;
  public username: any;
  public password: any;
  public authenticated: any;
  // public user: User = {};

  constructor(private http: HttpClient) {

  }

  authenticationService(username: string, password: string, authenticated: number){
    console.log('Username en auth:', username);
    console.log('Password en auth:', password);

    // Datos a enviar en el cuerpo de la solicitud POST
    const body = { username, password, authenticated };

    return this.http.post(`http://127.0.0.1:8081/login`, body).pipe(
      map((res: any) => {
        this.username = res.username;
        this.password = res.password;
        this.userName = res.name;
        this.authenticated = res.authenticated;
        const user: User = {
          "id": res.id,
          "username": res.username,
          "userType": res.userType,
          "password": res.password,
          "cedula": res.cedula,
          "name": res.name,
          "lastname": res.lastname,
          "address": res.address,
          "cedulaJuridica": res.cedulaJuridica,
          "razonSocial": res.razonSocial,
          "email": res.email,
          "authenticated": res.authenticated
      }
        this.registerSuccessfulLogin(this.username, this.password, this.userName, this.authenticated, user);
        return res; // Devuelve la respuesta del servidor si es necesario
      })
    );
  }

  createBasicAuthToken(username: String, password: String) {
    return 'Basic ' + window.btoa(username + ":" + password)
  }

  registerSuccessfulLogin(username: string, password: string, userName: string, authenticated: number, user: User) {
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_AUTH_TOKEN, this.createBasicAuthToken(username, password));
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME, this.userName);
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_USERNAME, this.username);
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_PASSWORD, this.password);
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_AUTHENTICATED, this.authenticated);
    const loggedUser = JSON.stringify(user);
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_USER, loggedUser);
  }

  logout() {
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_USERNAME);
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_PASSWORD);
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_AUTHENTICATED);
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_USER);
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_AUTH_TOKEN);

    this.username = null;
    this.password = null;
    this.authenticated = null;
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_AUTHENTICATED)
    if (user === null) return false
    return true
  }

  getLoggedInName() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME)
    if (user === null) return ''
    return user
  }

  getLoggedInUsername() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_USERNAME)
    if (user === null) return ''
    return user
  }

  getLoggedInPassword() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_PASSWORD)
    if (user === null) return ''
    return user
  }

  getAuthToken() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_AUTH_TOKEN);
    if (user === null) return ''
    return user
  }

  getAuthenticatedStatus() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_AUTHENTICATED)
    if (user === null) return ''
    return user
  }

  getLoggedInUser() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_USER)
    if (user === null) return ''
    return user
  }
}
