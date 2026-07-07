import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BmsBaseComponent } from './BmsBaseComponent';
import { ProtectedFieldsDirective } from '../protected_fields.directive';

@Component({
	selector: 'app-COUSR00-COUSR0A',
	templateUrl: './COUSR00_COUSR0A.html',
	standalone: true,
	imports: [CommonModule, FormsModule, ReactiveFormsModule, ProtectedFieldsDirective ],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class COUSR00_COUSR0A extends BmsBaseComponent implements BmsMap {

	COUSR00_COUSR0A_FORM! : FormGroup;

	constructor(
		appService: AppService,
		formBuilder: FormBuilder,
	) {
		super(appService, formBuilder);
	}

	ngOnInit() {
		this.COUSR00_COUSR0A_FORM = this.formBuilder.group({
			TRNNAME: '',
			TITLE01: '',
			CURDATE: '',
			PGMNAME: '',
			TITLE02: '',
			CURTIME: '',
			PAGENUM: '',
			USRIDIN: '',
			SEL0001: '',
			USRID01: '',
			FNAME01: '',
			LNAME01: '',
			UTYPE01: '',
			SEL0002: '',
			USRID02: '',
			FNAME02: '',
			LNAME02: '',
			UTYPE02: '',
			SEL0003: '',
			USRID03: '',
			FNAME03: '',
			LNAME03: '',
			UTYPE03: '',
			SEL0004: '',
			USRID04: '',
			FNAME04: '',
			LNAME04: '',
			UTYPE04: '',
			SEL0005: '',
			USRID05: '',
			FNAME05: '',
			LNAME05: '',
			UTYPE05: '',
			SEL0006: '',
			USRID06: '',
			FNAME06: '',
			LNAME06: '',
			UTYPE06: '',
			SEL0007: '',
			USRID07: '',
			FNAME07: '',
			LNAME07: '',
			UTYPE07: '',
			SEL0008: '',
			USRID08: '',
			FNAME08: '',
			LNAME08: '',
			UTYPE08: '',
			SEL0009: '',
			USRID09: '',
			FNAME09: '',
			LNAME09: '',
			UTYPE09: '',
			SEL0010: '',
			USRID10: '',
			FNAME10: '',
			LNAME10: '',
			UTYPE10: '',
			ERRMSG: ''
		});
	}

	getMapName(): string {
		return "COUSR00_COUSR0A";
	}

	getForm(): FormGroup {
		return this.COUSR00_COUSR0A_FORM;
	}

}
