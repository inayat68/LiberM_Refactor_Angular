import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse} from '@angular/common/http';
import { Observable, throwError, EMPTY } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { ErrorService } from './error.service';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private errorService: ErrorService,
              private router: Router) {
    //console.log('ErrorInterceptor constructed');
  }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    //console.log('ErrorInterceptor: called for ', req.url);
    return next.handle(req).pipe(
      catchError((err: HttpErrorResponse) => {
        //console.log('ErrorInterceptor: status -> ', err.status, ' url -> ', req.url);
        const isApiCall = req.url.startsWith('api/');
        const isConnectCall = req.url === 'api/connect';
        //Handle browser timeout
        if (isApiCall && !isConnectCall && (err.status === 0 || err.status === 401 || err.status === 403)) {
          console.warn('Session timeout, redirecting to the initial page: ', err);
          const message = 'Your session has expired.';
          this.errorService.setError(err.status, message);
          //window.location.href = '/';
          //this.router.navigateByUrl('/nibservice/');
          //const newUrl = window.location.origin + '/nibservice/';
          //window.location.href = newUrl;
          return EMPTY;
        }
        if (isConnectCall) {
          console.warn('Angular frontend cannot connect to the CICS region: ', err);
          const message = 'Cannot connect to the CICS region.';
          this.errorService.setError(err.status, message);
          //window.location.href = '/';
          //this.router.navigateByUrl('/nibservice/');
          //const newUrl = window.location.origin + '/nibservice/';
          //window.location.href = newUrl;
          //return EMPTY;
          return throwError(() => err);
        }
        const message = err.error?.message || 'Unhandled exception';
        this.errorService.setError(err.status, message);
        return throwError(() => err);
      })
    );
  }
}
