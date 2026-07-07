import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BmsBaseComponent } from './BmsBaseComponent';
import { ProtectedFieldsDirective } from '../protected_fields.directive';

@Component({
	selector: 'app-CORPT00-CORPT0A',
	templateUrl: './CORPT00_CORPT0A.html',
	standalone: true,
	imports: [CommonModule, FormsModule, ReactiveFormsModule, ProtectedFieldsDirective ],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class CORPT00_CORPT0A extends BmsBaseComponent implements BmsMap {

	CORPT00_CORPT0A_FORM! : FormGroup;

	constructor(
		appService: AppService,
		formBuilder: FormBuilder,
	) {
		super(appService, formBuilder);
	}

	ngOnInit() {
		this.CORPT00_CORPT0A_FORM = this.formBuilder.group({
			TRNNAME: '',
			TITLE01: '',
			CURDATE: '',
			PGMNAME: '',
			TITLE02: '',
			CURTIME: '',
			MONTHLY: '',
			YEARLY: '',
			CUSTOM: '',
			SDTMM: '',
			SDTDD: '',
			SDTYYYY: '',
			EDTMM: '',
			EDTDD: '',
			EDTYYYY: '',
			CONFIRM: '',
			ERRMSG: ''
		});
	}

	getMapName(): string {
		return "CORPT00_CORPT0A";
	}

	getForm(): FormGroup {
		return this.CORPT00_CORPT0A_FORM;
	}

}
