import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BmsBaseComponent } from './BmsBaseComponent';
import { ProtectedFieldsDirective } from '../protected_fields.directive';

@Component({
	selector: 'app-COBIL00-COBIL0A',
	templateUrl: './COBIL00_COBIL0A.html',
	standalone: true,
	imports: [CommonModule, FormsModule, ReactiveFormsModule, ProtectedFieldsDirective ],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class COBIL00_COBIL0A extends BmsBaseComponent implements BmsMap {

	COBIL00_COBIL0A_FORM! : FormGroup;

	constructor(
		appService: AppService,
		formBuilder: FormBuilder,
	) {
		super(appService, formBuilder);
	}

	ngOnInit() {
		this.COBIL00_COBIL0A_FORM = this.formBuilder.group({
			TRNNAME: '',
			TITLE01: '',
			CURDATE: '',
			PGMNAME: '',
			TITLE02: '',
			CURTIME: '',
			ACTIDIN: '',
			CURBAL: '',
			CONFIRM: '',
			ERRMSG: ''
		});
	}

	getMapName(): string {
		return "COBIL00_COBIL0A";
	}

	getForm(): FormGroup {
		return this.COBIL00_COBIL0A_FORM;
	}

}
