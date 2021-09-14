import {Component, Input, OnInit} from '@angular/core';
import {Post} from "../../model/Post";
import {StorageService} from "../../services/storage.service";

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  @Input() post: Post;

  constructor(private storageService: StorageService) { }

  ngOnInit(): void {
  }

  isUserLogged() {
    return !!this.storageService.getLocalUser();
  }

  createdByLoggedUser(post: Post) {
    return this.storageService.getLocalUser().email === post.user?.email;
  }

}
