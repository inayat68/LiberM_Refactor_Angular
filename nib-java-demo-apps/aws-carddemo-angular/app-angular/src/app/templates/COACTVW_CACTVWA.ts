import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BmsBaseComponent } from './BmsBaseComponent';
import { ProtectedFieldsDirective } from '../protected_fields.directive';

@Component({
	selector: 'app-COACTVW-CACTVWA',
	templateUrl: './COACTVW_CACTVWA.html',
	standalone: true,
	imports: [CommonModule, FormsModule, ReactiveFormsModule, ProtectedFieldsDirective ],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class COACTVW_CACTVWA extends BmsBaseComponent implements BmsMap {

	COACTVW_CACTVWA_FORM! : FormGroup;

	constructor(
		appService: AppService,
		formBuilder: FormBuilder,
	) {
		super(appService, formBuilder);
	}

	ngOnInit() {
		this.COACTVW_CACTVWA_FORM = this.formBuilder.group({
			TRNNAME: '',
			TITLE01: '',
			CURDATE: '',
			PGMNAME: '',
			TITLE02: '',
			CURTIME: '',
			ACCTSID: '',
			ACSTTUS: '',
			ADTOPEN: '',
			ACRDLIM: '',
			AEXPDT: '',
			ACSHLIM: '',
			AREISDT: '',
			ACURBAL: '',
			ACRCYCR: '',
			AADDGRP: '',
			ACRCYDB: '',
			ACSTNUM: '',
			ACSTSSN: '',
			ACSTDOB: '',
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
			ACSPHN1: '',
			ACSGOVT: '',
			ACSPHN2: '',
			ACSEFTC: '',
			ACSPFLG: '',
			INFOMSG: '',
			ERRMSG: ''
		});
	}

	getMapName(): string {
		return "COACTVW_CACTVWA";
	}

	getForm(): FormGroup {
		return this.COACTVW_CACTVWA_FORM;
	}

}
