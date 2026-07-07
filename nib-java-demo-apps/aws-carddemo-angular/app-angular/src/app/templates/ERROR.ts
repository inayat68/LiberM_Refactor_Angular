import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppService } from '../app.service';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

@Component({
  selector: 'app-ERROR',
  templateUrl: './ERROR.html',
	standalone: true,
  imports: [CommonModule],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ERROR {
  constructor(public appService: AppService) {}
}
