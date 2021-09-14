import { Component, OnInit } from '@angular/core';
import {MatBottomSheet} from "@angular/material/bottom-sheet";
import {AuthCardComponent} from "../../components/auth-card/auth-card.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private authSheet: MatBottomSheet,
              private router: Router) { }

  ngOnInit(): void {
  }

  onCreatAccountClick() {
    this.authSheet.open(AuthCardComponent);
  }

  onNotNowClick() {
    this.router.navigate(['feed']);
  }

}
