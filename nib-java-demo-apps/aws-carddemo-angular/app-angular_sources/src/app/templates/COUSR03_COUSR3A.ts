import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BmsBaseComponent } from './BmsBaseComponent';
import { ProtectedFieldsDirective } from '../protected_fields.directive';

@Component({
	selector: 'app-COUSR03-COUSR3A',
	templateUrl: './COUSR03_COUSR3A.html',
	standalone: true,
	imports: [CommonModule, FormsModule, ReactiveFormsModule, ProtectedFieldsDirective ],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class COUSR03_COUSR3A extends BmsBaseComponent implements BmsMap {

	COUSR03_COUSR3A_FORM! : FormGroup;

	constructor(
		appService: AppService,
		formBuilder: FormBuilder,
	) {
		super(appService, formBuilder);
	}

	ngOnInit() {
		this.COUSR03_COUSR3A_FORM = this.formBuilder.group({
			TRNNAME: '',
			TITLE01: '',
			CURDATE: '',
			PGMNAME: '',
			TITLE02: '',
			CURTIME: '',
			USRIDIN: '',
			FNAME: '',
			LNAME: '',
			USRTYPE: '',
			ERRMSG: ''
		});
	}

	getMapName(): string {
		return "COUSR03_COUSR3A";
	}

	getForm(): FormGroup {
		return this.COUSR03_COUSR3A_FORM;
	}

}
