import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BmsBaseComponent } from './BmsBaseComponent';

@Component({
	selector: 'app-COSGN00-COSGN0A-NIB',
	templateUrl: './COSGN00_COSGN0A_NIB.html',
	standalone: true,
	imports: [CommonModule, FormsModule, ReactiveFormsModule ],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class COSGN00_COSGN0A_NIB extends BmsBaseComponent implements BmsMap {

	COSGN00_COSGN0A_NIB_FORM = this.formBuilder.group({
		USERID: '',
		PASSWD: ''
	});

	constructor(
		appService: AppService,
		formBuilder: FormBuilder,
	) {
		super(appService, formBuilder);
	}

	getMapName(): string {
		return "COSGN00_COSGN0A_NIB";
	}

	getForm(): FormGroup {
		return this.COSGN00_COSGN0A_NIB_FORM;
	}

}
