import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BmsBaseComponent } from './BmsBaseComponent';
import { ProtectedFieldsDirective } from '../protected_fields.directive';

@Component({
	selector: 'app-COACTUP-CACTUPA',
	templateUrl: './COACTUP_CACTUPA.html',
	standalone: true,
	imports: [CommonModule, FormsModule, ReactiveFormsModule, ProtectedFieldsDirective ],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class COACTUP_CACTUPA extends BmsBaseComponent implements BmsMap {

	COACTUP_CACTUPA_FORM! : FormGroup;

	constructor(
		appService: AppService,
		formBuilder: FormBuilder,
	) {
		super(appService, formBuilder);
	}

	ngOnInit() {
		this.COACTUP_CACTUPA_FORM = this.formBuilder.group({
			TRNNAME: '',
			TITLE01: '',
			CURDATE: '',
			PGMNAME: '',
			TITLE02: '',
			CURTIME: '',
			ACCTSID: '',
			ACSTTUS: '',
			OPNYEAR: '',
			OPNMON: '',
			OPNDAY: '',
			ACRDLIM: '',
			EXPYEAR: '',
			EXPMON: '',
			EXPDAY: '',
			ACSHLIM: '',
			RISYEAR: '',
			RISMON: '',
			RISDAY: '',
			ACURBAL: '',
			ACRCYCR: '',
			AADDGRP: '',
			ACRCYDB: '',
			ACSTNUM: '',
			ACTSSN1: '',
			ACTSSN2: '',
			ACTSSN3: '',
			DOBYEAR: '',
			DOBMON: '',
			DOBDAY: '',
			ACSTFCO: '',
			ACSFNAM: '',
			ACSMNAM: '',
			ACSLNAM: '',
			ACSADL1: '',
			ACSSTTE: '',
			ACSADL2: '',
			ACSZIPC: '',
			ACSCITY: '',
			ACSCTRY: '',
			ACSPH1A: '',
			ACSPH1B: '',
			ACSPH1C: '',
			ACSGOVT: '',
			ACSPH2A: '',
			ACSPH2B: '',
			ACSPH2C: '',
			ACSEFTC: '',
			ACSPFLG: '',
			INFOMSG: '',
			ERRMSG: '',
			FKEYS: '',
			FKEY05: '',
			FKEY12: ''
		});
	}

	getMapName(): string {
		return "COACTUP_CACTUPA";
	}

	getForm(): FormGroup {
		return this.COACTUP_CACTUPA_FORM;
	}

}
