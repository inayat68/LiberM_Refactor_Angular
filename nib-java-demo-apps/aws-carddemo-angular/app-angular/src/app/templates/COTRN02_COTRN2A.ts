import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BmsBaseComponent } from './BmsBaseComponent';
import { ProtectedFieldsDirective } from '../protected_fields.directive';

@Component({
	selector: 'app-COTRN02-COTRN2A',
	templateUrl: './COTRN02_COTRN2A.html',
	standalone: true,
	imports: [CommonModule, FormsModule, ReactiveFormsModule, ProtectedFieldsDirective ],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class COTRN02_COTRN2A extends BmsBaseComponent implements BmsMap {

	COTRN02_COTRN2A_FORM! : FormGroup;

	constructor(
		appService: AppService,
		formBuilder: FormBuilder,
	) {
		super(appService, formBuilder);
	}

	ngOnInit() {
		this.COTRN02_COTRN2A_FORM = this.formBuilder.group({
			TRNNAME: '',
			TITLE01: '',
			CURDATE: '',
			PGMNAME: '',
			TITLE02: '',
			CURTIME: '',
			ACTIDIN: '',
			CARDNIN: '',
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
			CONFIRM: '',
			ERRMSG: ''
		});
	}

	getMapName(): string {
		return "COTRN02_COTRN2A";
	}

	getForm(): FormGroup {
		return this.COTRN02_COTRN2A_FORM;
	}

}
