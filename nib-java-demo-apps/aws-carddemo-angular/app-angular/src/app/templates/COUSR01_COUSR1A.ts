import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BmsBaseComponent } from './BmsBaseComponent';
import { ProtectedFieldsDirective } from '../protected_fields.directive';

@Component({
	selector: 'app-COUSR01-COUSR1A',
	templateUrl: './COUSR01_COUSR1A.html',
	standalone: true,
	imports: [CommonModule, FormsModule, ReactiveFormsModule, ProtectedFieldsDirective ],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class COUSR01_COUSR1A extends BmsBaseComponent implements BmsMap {

	COUSR01_COUSR1A_FORM! : FormGroup;

	constructor(
		appService: AppService,
		formBuilder: FormBuilder,
	) {
		super(appService, formBuilder);
	}

	ngOnInit() {
		this.COUSR01_COUSR1A_FORM = this.formBuilder.group({
			TRNNAME: '',
			TITLE01: '',
			CURDATE: '',
			PGMNAME: '',
			TITLE02: '',
			CURTIME: '',
			FNAME: '',
			LNAME: '',
			USERID: '',
			PASSWD: '',
			USRTYPE: '',
			ERRMSG: ''
		});
	}

	getMapName(): string {
		return "COUSR01_COUSR1A";
	}

	getForm(): FormGroup {
		return this.COUSR01_COUSR1A_FORM;
	}

}
