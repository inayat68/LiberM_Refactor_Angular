import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BmsBaseComponent } from './BmsBaseComponent';
import { ProtectedFieldsDirective } from '../protected_fields.directive';

@Component({
	selector: 'app-COSGN00-COSGN0A',
	templateUrl: './COSGN00_COSGN0A.html',
	standalone: true,
	imports: [CommonModule, FormsModule, ReactiveFormsModule, ProtectedFieldsDirective ],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class COSGN00_COSGN0A extends BmsBaseComponent implements BmsMap {

	COSGN00_COSGN0A_FORM! : FormGroup;

	constructor(
		appService: AppService,
		formBuilder: FormBuilder,
	) {
		super(appService, formBuilder);
	}

	ngOnInit() {
		this.COSGN00_COSGN0A_FORM = this.formBuilder.group({
			TRNNAME: '',
			TITLE01: '',
			CURDATE: '',
			PGMNAME: '',
			TITLE02: '',
			CURTIME: '',
			APPLID: '',
			SYSID: '',
			USERID: '',
			PASSWD: '',
			ERRMSG: ''
		});
	}

	getMapName(): string {
		return "COSGN00_COSGN0A";
	}

	getForm(): FormGroup {
		return this.COSGN00_COSGN0A_FORM;
	}

}
