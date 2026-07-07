import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient,
         withInterceptorsFromDi } from '@angular/common/http';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppComponent } from './app/app.component';
import { ErrorInterceptor } from './app/error.interceptor';
import { appConfig } from './app/app.config';
//Security integration
//import { withXsrfConfiguration } from '@angular/common/http';
//import { importProvidersFrom } from '@angular/core';
//import { OAuthModule, OAuthService } from 'angular-oauth2-oidc';
//import { authConfig } from './app/auth.config';

//export function initializeAuth(oauthService: OAuthService): () => Promise<void> {
//  return () =>
//    new Promise<void>((resolve) => {
//      oauthService.configure(authConfig);
//      oauthService.setStorage(sessionStorage);
//      oauthService.setupAutomaticSilentRefresh();   // ⭐ CRITICAL FIX ⭐
//      oauthService.loadDiscoveryDocumentAndLogin().then(() => resolve());
//    });
//}

bootstrapApplication(AppComponent, {
  providers: [
    ...(appConfig.providers ?? []),
    provideHttpClient(withInterceptorsFromDi()),
    // Make OAuthModule available application-wide
    // importProvidersFrom(OAuthModule.forRoot()),
    // Initialize BEFORE app starts
    //{
    //  provide: 'APP_INITIALIZER',
    //  useFactory: initializeAuth,
    //  deps: [OAuthService],
    //  multi: true,
    //},
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true,
    },
  ],
  }).catch((err) => console.error(err));
