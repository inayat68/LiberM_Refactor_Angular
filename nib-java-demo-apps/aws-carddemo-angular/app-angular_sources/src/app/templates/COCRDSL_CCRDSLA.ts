import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BmsBaseComponent } from './BmsBaseComponent';
import { ProtectedFieldsDirective } from '../protected_fields.directive';

@Component({
	selector: 'app-COCRDSL-CCRDSLA',
	templateUrl: './COCRDSL_CCRDSLA.html',
	standalone: true,
	imports: [CommonModule, FormsModule, ReactiveFormsModule, ProtectedFieldsDirective ],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class COCRDSL_CCRDSLA extends BmsBaseComponent implements BmsMap {

	COCRDSL_CCRDSLA_FORM! : FormGroup;

	constructor(
		appService: AppService,
		formBuilder: FormBuilder,
	) {
		super(appService, formBuilder);
	}

	ngOnInit() {
		this.COCRDSL_CCRDSLA_FORM = this.formBuilder.group({
			TRNNAME: '',
			TITLE01: '',
			CURDATE: '',
			PGMNAME: '',
			TITLE02: '',
			CURTIME: '',
			ACCTSID: '',
			CARDSID: '',
			CRDNAME: '',
			CRDSTCD: '',
			EXPMON: '',
			EXPYEAR: '',
			INFOMSG: '',
			ERRMSG: '',
			FKEYS: ''
		});
	}

	getMapName(): string {
		return "COCRDSL_CCRDSLA";
	}

	getForm(): FormGroup {
		return this.COCRDSL_CCRDSLA_FORM;
	}

}
