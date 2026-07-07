import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BmsBaseComponent } from './BmsBaseComponent';
import { ProtectedFieldsDirective } from '../protected_fields.directive';

@Component({
	selector: 'app-COMEN01-COMEN1A',
	templateUrl: './COMEN01_COMEN1A.html',
	standalone: true,
	imports: [CommonModule, FormsModule, ReactiveFormsModule, ProtectedFieldsDirective ],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class COMEN01_COMEN1A extends BmsBaseComponent implements BmsMap {

	COMEN01_COMEN1A_FORM! : FormGroup;

	constructor(
		appService: AppService,
		formBuilder: FormBuilder,
	) {
		super(appService, formBuilder);
	}

	ngOnInit() {
		this.COMEN01_COMEN1A_FORM = this.formBuilder.group({
			TRNNAME: '',
			TITLE01: '',
			CURDATE: '',
			PGMNAME: '',
			TITLE02: '',
			CURTIME: '',
			OPTN001: '',
			OPTN002: '',
			OPTN003: '',
			OPTN004: '',
			OPTN005: '',
			OPTN006: '',
			OPTN007: '',
			OPTN008: '',
			OPTN009: '',
			OPTN010: '',
			OPTN011: '',
			OPTN012: '',
			OPTION: '',
			ERRMSG: ''
		});
	}

	getMapName(): string {
		return "COMEN01_COMEN1A";
	}

	getForm(): FormGroup {
		return this.COMEN01_COMEN1A_FORM;
	}

}
