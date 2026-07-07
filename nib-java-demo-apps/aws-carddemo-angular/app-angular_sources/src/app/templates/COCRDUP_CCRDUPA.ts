import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BmsBaseComponent } from './BmsBaseComponent';
import { ProtectedFieldsDirective } from '../protected_fields.directive';

@Component({
	selector: 'app-COCRDUP-CCRDUPA',
	templateUrl: './COCRDUP_CCRDUPA.html',
	standalone: true,
	imports: [CommonModule, FormsModule, ReactiveFormsModule, ProtectedFieldsDirective ],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class COCRDUP_CCRDUPA extends BmsBaseComponent implements BmsMap {

	COCRDUP_CCRDUPA_FORM! : FormGroup;

	constructor(
		appService: AppService,
		formBuilder: FormBuilder,
	) {
		super(appService, formBuilder);
	}

	ngOnInit() {
		this.COCRDUP_CCRDUPA_FORM = this.formBuilder.group({
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
			EXPDAY: '',
			INFOMSG: '',
			ERRMSG: '',
			FKEYS: '',
			FKEYSC: ''
		});
	}

	getMapName(): string {
		return "COCRDUP_CCRDUPA";
	}

	getForm(): FormGroup {
		return this.COCRDUP_CCRDUPA_FORM;
	}

}
