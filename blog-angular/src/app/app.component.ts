import { Component } from '@angular/core';
import {MatBottomSheet} from "@angular/material/bottom-sheet";
import {AuthCardComponent} from "./components/auth-card/auth-card.component";
import {AuthService} from "./services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private authSheet: MatBottomSheet,
              private authService: AuthService,
              private router: Router) {
    if(this.isLoggedIn()) {
      this.router.navigate(['feed']);
    }
  }

  onLoginClick() {
    const authSheetRef = this.authSheet.open(AuthCardComponent);
    authSheetRef.afterDismissed().subscribe(result => {
      if(result) {
        this.router.navigate(['feed']);
      }
    });
  }

  onLogoutClick() {
    this.authService.logout();
  }

  isLoggedIn() {
    return !!this.authService.storage.getLocalUser()?.token;
  }
}
