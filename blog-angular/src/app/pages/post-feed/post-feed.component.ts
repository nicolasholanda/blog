import { Component, OnInit } from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {CreatePostComponent} from "../../components/create-post/create-post.component";
import {PostService} from "../../services/post.service";
import {QueryParams} from "../../model/QueryParams";
import {Post} from "../../model/Post";
import {MessageService} from "../../services/message.service";
import {AuthService} from "../../services/auth.service";
import {StorageService} from "../../services/storage.service";

@Component({
  selector: 'app-post-feed',
  templateUrl: './post-feed.component.html',
  styleUrls: ['./post-feed.component.css']
})
export class PostFeedComponent implements OnInit {

  constructor(private dialog: MatDialog,
              private service: PostService,
              private messageService: MessageService,
              private storageService: StorageService) { }

  posts: Post[] = [];

  ngOnInit(): void {
    let params: QueryParams = {
      page: 0,
      direction: 'DESC',
      linesPerPage: 10,
      orderBy: 'creationDate'
    };
    this.service.getPaginated(params).subscribe(
      response => this.posts = response,
      error => this.messageService.catchError(error.error));
  }

  onNewPost() {
    this.dialog.open(CreatePostComponent, {
      width: '40%'
    });
  }

  isUserLogged() {
    return !!this.storageService.getLocalUser();
  }

}
