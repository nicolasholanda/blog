import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PostService} from "../../services/post.service";
import {Post} from "../../model/Post";
import {MessageService} from "../../services/message.service";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  postForm: FormGroup;

  constructor(private fb: FormBuilder,
              private service: PostService,
              private messageService: MessageService,
              private dialogRef: MatDialogRef<CreatePostComponent>) {
    this.postForm = fb.group({
      title: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(255)]],
      content: ['', [Validators.required, Validators.minLength(3)]],
    });
  }

  ngOnInit(): void {
  }

  onCreatePost() {
    let post: Post = {
      title: this.postForm.get('title')?.value,
      content: this.postForm.get('content')?.value,
      user: null,
      updateDate: null,
      creationDate: null
    };

    this.service.create(post).subscribe(
      () => {
        this.messageService.successMessage('Postagem criada.')
        this.dialogRef.close();
      }, error => this.messageService.catchError(error.error))
  }

}
