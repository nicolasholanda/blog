import { Injectable } from '@angular/core';
import {LocalUser} from "../model/LocalUser";

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  getLocalUser(): LocalUser {
    let usr = localStorage.getItem("localUser");
    return usr !== null ? JSON.parse(usr) : usr;
  }

  setLocalUser(obj: LocalUser | null) {
    if (!obj) {
      localStorage.removeItem("localUser");
    } else {
      localStorage.setItem("localUser", JSON.stringify(obj));
    }
  }
}
