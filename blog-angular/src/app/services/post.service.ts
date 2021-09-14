import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {API_CONFIG} from "../config/api.config";
import {Post} from "../model/Post";
import {QueryParams} from "../model/QueryParams";
import {map} from "rxjs/operators";
import {PaginatedPosts} from "../model/PaginatedPosts";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) {
  }

  create(post: Post): Observable<Post> {
    return this.http.post<Post>(`${API_CONFIG.baseUrl}/posts`, post);
  }

  getPaginated(params: QueryParams): Observable<Post[]> {
    return this.http.get<PaginatedPosts>(`${API_CONFIG.baseUrl}/posts`, {
      params: {...params}
    }).pipe(map(data => {
      return data.content;
    }));
  }
}
