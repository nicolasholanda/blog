import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {API_CONFIG} from "../config/api.config";
import {tap} from "rxjs/operators";
import {NewUser} from "../model/NewUser";
import {Observable} from "rxjs";
import {User} from "../model/User";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient,
              private auth: AuthService) {
  }

  create(user: NewUser): Observable<User> {
    return this.http.post<User>(`${API_CONFIG.baseUrl}/users`, user);
  }
}
