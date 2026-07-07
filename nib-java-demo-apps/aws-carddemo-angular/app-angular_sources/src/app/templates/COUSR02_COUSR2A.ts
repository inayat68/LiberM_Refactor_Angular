import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BmsBaseComponent } from './BmsBaseComponent';
import { ProtectedFieldsDirective } from '../protected_fields.directive';

@Component({
	selector: 'app-COUSR02-COUSR2A',
	templateUrl: './COUSR02_COUSR2A.html',
	standalone: true,
	imports: [CommonModule, FormsModule, ReactiveFormsModule, ProtectedFieldsDirective ],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class COUSR02_COUSR2A extends BmsBaseComponent implements BmsMap {

	COUSR02_COUSR2A_FORM! : FormGroup;

	constructor(
		appService: AppService,
		formBuilder: FormBuilder,
	) {
		super(appService, formBuilder);
	}

	ngOnInit() {
		this.COUSR02_COUSR2A_FORM = this.formBuilder.group({
			TRNNAME: '',
			TITLE01: '',
			CURDATE: '',
			PGMNAME: '',
			TITLE02: '',
			CURTIME: '',
			USRIDIN: '',
			FNAME: '',
			LNAME: '',
			PASSWD: '',
			USRTYPE: '',
			ERRMSG: ''
		});
	}

	getMapName(): string {
		return "COUSR02_COUSR2A";
	}

	getForm(): FormGroup {
		return this.COUSR02_COUSR2A_FORM;
	}

}
