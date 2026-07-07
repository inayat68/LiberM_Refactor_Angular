import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BmsBaseComponent } from './BmsBaseComponent';
import { ProtectedFieldsDirective } from '../protected_fields.directive';

@Component({
	selector: 'app-COTRN00-COTRN0A',
	templateUrl: './COTRN00_COTRN0A.html',
	standalone: true,
	imports: [CommonModule, FormsModule, ReactiveFormsModule, ProtectedFieldsDirective ],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class COTRN00_COTRN0A extends BmsBaseComponent implements BmsMap {

	COTRN00_COTRN0A_FORM! : FormGroup;

	constructor(
		appService: AppService,
		formBuilder: FormBuilder,
	) {
		super(appService, formBuilder);
	}

	ngOnInit() {
		this.COTRN00_COTRN0A_FORM = this.formBuilder.group({
			TRNNAME: '',
			TITLE01: '',
			CURDATE: '',
			PGMNAME: '',
			TITLE02: '',
			CURTIME: '',
			PAGENUM: '',
			TRNIDIN: '',
			SEL0001: '',
			TRNID01: '',
			TDATE01: '',
			TDESC01: '',
			TAMT001: '',
			SEL0002: '',
			TRNID02: '',
			TDATE02: '',
			TDESC02: '',
			TAMT002: '',
			SEL0003: '',
			TRNID03: '',
			TDATE03: '',
			TDESC03: '',
			TAMT003: '',
			SEL0004: '',
			TRNID04: '',
			TDATE04: '',
			TDESC04: '',
			TAMT004: '',
			SEL0005: '',
			TRNID05: '',
			TDATE05: '',
			TDESC05: '',
			TAMT005: '',
			SEL0006: '',
			TRNID06: '',
			TDATE06: '',
			TDESC06: '',
			TAMT006: '',
			SEL0007: '',
			TRNID07: '',
			TDATE07: '',
			TDESC07: '',
			TAMT007: '',
			SEL0008: '',
			TRNID08: '',
			TDATE08: '',
			TDESC08: '',
			TAMT008: '',
			SEL0009: '',
			TRNID09: '',
			TDATE09: '',
			TDESC09: '',
			TAMT009: '',
			SEL0010: '',
			TRNID10: '',
			TDATE10: '',
			TDESC10: '',
			TAMT010: '',
			ERRMSG: ''
		});
	}

	getMapName(): string {
		return "COTRN00_COTRN0A";
	}

	getForm(): FormGroup {
		return this.COTRN00_COTRN0A_FORM;
	}

}
