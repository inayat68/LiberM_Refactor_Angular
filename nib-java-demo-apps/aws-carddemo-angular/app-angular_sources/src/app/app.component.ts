import {
  Component,
  OnInit,
  OnDestroy,
  ChangeDetectorRef,
  CUSTOM_ELEMENTS_SCHEMA,
  Type
} from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule, NgComponentOutlet } from '@angular/common';
import { Subscription } from 'rxjs';
import { AppService } from './app.service';
import { ErrorService, AppError } from './error.service';
import { NgxSpinnerModule } from 'ngx-spinner';
import { TEMPLATE_IMPORTS, TEMPLATE_REGISTRY } from './template-registry';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: true,
  imports: [
    CommonModule,
    NgxSpinnerModule,
    HttpClientModule,
    NgComponentOutlet,
    ...TEMPLATE_IMPORTS
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'aws-angular';
  response: any = null;
  error: AppError = { code: null, message: null };
  serverMessage = '';
  currentComponent: Type<any> | null = null;

  private sub!: Subscription;
  private componentSub!: Subscription;

  constructor(
    public appService: AppService,
    private cdRef: ChangeDetectorRef,
    private errorService: ErrorService
  ) {}
  /*
   * First start, when http://localhost:8088/nibservice/ is invoked
   *
   * Calls: com.nib.angular.service.controller.connect()
   *
   * and should get WELCOME as first map
   *
   * will get ERROR if an error happens
   *
   */
  ngOnInit() {
    this.sub = this.errorService.error$.subscribe(error => {
      this.error = error;
    });

    this.componentSub = this.appService.componentName$.subscribe(componentName => {
      this.currentComponent =
        TEMPLATE_REGISTRY[componentName as keyof typeof TEMPLATE_REGISTRY] ?? TEMPLATE_REGISTRY.ERROR;

      this.cdRef.detectChanges();
    });

    this.appService.connect().subscribe({
      next: (response: any) => {
        this.response = response;

        if (this.response?.sessionId == null) {
          this.errorService.setError(500, 'Cannot connect to the CICS region.');
        }

        if (response.orders) {
          const lastOrder = response.orders[response.orders.length - 1];
          this.appService.setComponentName(lastOrder.mapName);
        } else {
          // fallback, in case first screen should be WELCOME
          this.appService.setComponentName('WELCOME');
        }

        this.appService.termId = response.termId;
        this.appService.sessionId = response.sessionId;
        this.serverMessage = response?.errorMessage ?? '';
        this.appService.serverMessage = this.serverMessage;
        this.appService.currentResponse = response;
        this.appService.populateConnectionData();
        this.cdRef.detectChanges();
      },
      error: (err) => {
        this.response = { sessionId: null };

        if (err?.status === 0) {
          this.serverMessage = 'Unable to connect to server.';
          this.appService.serverMessage = this.serverMessage;
          console.error('Welcome page failed to load: server unreachable (status=0).', err);
          this.cdRef.detectChanges();
          return;
        }

        const status = err?.status ?? 'unknown';
        const message = err?.message ?? 'unknown error';

        this.serverMessage = `Request failed (${status}): ${message}`;
        this.appService.serverMessage = this.serverMessage;
        console.error(`Welcome page failed to load, status=[${status}], message=[${message}]`, err);
        this.cdRef.detectChanges();
      }
    });

    window.onbeforeunload = () => this.ngOnDestroy();
  }

  ngOnDestroy() {
    this.sub?.unsubscribe();
    this.componentSub?.unsubscribe();

    this.appService.disconnect().subscribe(
      () => {
        this.cdRef.detectChanges();
      },
      (error) => {
        console.error('Error loading HTML template:', error);
      }
    );
  }

  clearError() {
    this.error = { code: null, message: null };
    this.errorService.setError(null, null);
  }

  changeComponent() {
    const newComponent =
      this.appService.getComponentName() === 'WELCOME' ? 'ERROR' : 'WELCOME';
    //console.log('Changing component to:', newComponent);
    this.cdRef.detectChanges();  // Trigger change detection when the value changes
    this.appService.setComponentName(newComponent);
    this.cdRef.detectChanges();
  }
}
