import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {Observable} from 'rxjs';
import {StorageService} from "../services/storage.service";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(private storage: StorageService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const accessToken = this.storage.getLocalUser()?.token;
    return next.handle(TokenInterceptor.addAuthorizationHeader(req, accessToken));
  }

  private static addAuthorizationHeader(request: HttpRequest<any>, token: any): HttpRequest<any> {
    if (token) {
      return request.clone({setHeaders: {Authorization: `Bearer ${token}`}});
    }
    return request;
  }
}
