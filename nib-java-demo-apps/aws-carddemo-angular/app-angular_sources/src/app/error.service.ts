import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface AppError {
  code: number | null;
  message: string | null;
}

@Injectable({ providedIn: 'root' })
export class ErrorService {
  private errorSubject = new BehaviorSubject<AppError>({ code: null, message: null });
  public code: number = 0;
  public message: string = '';
  error$ = this.errorSubject.asObservable();

  setError(code: number | null, message: string | null) {
    if (code != null) {
      this.code = code;
    }
    if (message != null) {
      this.message = message;
    }
    this.errorSubject.next({ code, message });
  }

  clearError() {
    this.errorSubject.next({ code: null, message: null });
  }
}
