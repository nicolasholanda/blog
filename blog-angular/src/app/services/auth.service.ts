import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Credentials} from "../model/Credentials";
import {API_CONFIG} from "../config/api.config";
import {JwtHelperService} from "@auth0/angular-jwt";
import {LocalUser} from "../model/LocalUser";
import {StorageService} from "./storage.service";
import {tap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(public http: HttpClient,
              public storage: StorageService) {
  }

  private refreshTokenTimeout = 0;
  private REFRESH_INTERVAL = 45000;

  authenticate(creds: Credentials) {
    return this.http.post(`${API_CONFIG.baseUrl}/login`,
      creds, {observe: 'response', responseType: 'text'})
      .pipe(
        tap(response => {
          this.successfulLogin(response.headers.get('Authorization'));
        })
      );
  }

  refreshToken() {
    return this.http.get(`${API_CONFIG.baseUrl}/auth/token`,
      {observe: 'response', responseType: 'text'})
      .pipe(
        tap(response => {
          this.successfulLogin(response.headers.get('Authorization'));
        }, () => {
          this.storage.setLocalUser(null);
          this.stopRefreshTokenTimer();
        })
      );
  }

  private successfulLogin(authorizationValue: string | null) {
    if (authorizationValue) {
      let tok = authorizationValue.substring(7);
      let user: LocalUser = {
        token: tok,
        email: this.jwtHelper.decodeToken(tok).sub
      };
      this.storage.setLocalUser(user);
      this.startRefreshTokenTimer();
    }
  }

  private startRefreshTokenTimer() {
    this.refreshTokenTimeout = setTimeout(() => this.refreshToken().subscribe(), this.REFRESH_INTERVAL);
  }

  private stopRefreshTokenTimer() {
    clearTimeout(this.refreshTokenTimeout);
  }

  logout() {
    this.storage.setLocalUser(null);
    this.stopRefreshTokenTimer();
    window.location.reload();
  }
}
