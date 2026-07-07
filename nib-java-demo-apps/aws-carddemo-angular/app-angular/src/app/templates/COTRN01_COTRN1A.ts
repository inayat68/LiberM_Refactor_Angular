import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BmsBaseComponent } from './BmsBaseComponent';
import { ProtectedFieldsDirective } from '../protected_fields.directive';

@Component({
	selector: 'app-COTRN01-COTRN1A',
	templateUrl: './COTRN01_COTRN1A.html',
	standalone: true,
	imports: [CommonModule, FormsModule, ReactiveFormsModule, ProtectedFieldsDirective ],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class COTRN01_COTRN1A extends BmsBaseComponent implements BmsMap {

	COTRN01_COTRN1A_FORM! : FormGroup;

	constructor(
		appService: AppService,
		formBuilder: FormBuilder,
	) {
		super(appService, formBuilder);
	}

	ngOnInit() {
		this.COTRN01_COTRN1A_FORM = this.formBuilder.group({
			TRNNAME: '',
			TITLE01: '',
			CURDATE: '',
			PGMNAME: '',
			TITLE02: '',
			CURTIME: '',
			TRNIDIN: '',
			TRNID: '',
			CARDNUM: '',
			TTYPCD: '',
			TCATCD: '',
			TRNSRC: '',
			TDESC: '',
			TRNAMT: '',
			TORIGDT: '',
			TPROCDT: '',
			MID: '',
			MNAME: '',
			MCITY: '',
			MZIP: '',
			ERRMSG: ''
		});
	}

	getMapName(): string {
		return "COTRN01_COTRN1A";
	}

	getForm(): FormGroup {
		return this.COTRN01_COTRN1A_FORM;
	}

}
