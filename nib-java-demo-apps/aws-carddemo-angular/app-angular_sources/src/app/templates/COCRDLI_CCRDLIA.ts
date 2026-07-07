import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BmsBaseComponent } from './BmsBaseComponent';
import { ProtectedFieldsDirective } from '../protected_fields.directive';

@Component({
	selector: 'app-COCRDLI-CCRDLIA',
	templateUrl: './COCRDLI_CCRDLIA.html',
	standalone: true,
	imports: [CommonModule, FormsModule, ReactiveFormsModule, ProtectedFieldsDirective ],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class COCRDLI_CCRDLIA extends BmsBaseComponent implements BmsMap {

	COCRDLI_CCRDLIA_FORM! : FormGroup;

	constructor(
		appService: AppService,
		formBuilder: FormBuilder,
	) {
		super(appService, formBuilder);
	}

	ngOnInit() {
		this.COCRDLI_CCRDLIA_FORM = this.formBuilder.group({
			TRNNAME: '',
			TITLE01: '',
			CURDATE: '',
			PGMNAME: '',
			TITLE02: '',
			CURTIME: '',
			PAGENO: '',
			ACCTSID: '',
			CARDSID: '',
			CRDSEL1: '',
			ACCTNO1: '',
			CRDNUM1: '',
			CRDSTS1: '',
			CRDSEL2: '',
			CRDSTP2: '',
			ACCTNO2: '',
			CRDNUM2: '',
			CRDSTS2: '',
			CRDSEL3: '',
			CRDSTP3: '',
			ACCTNO3: '',
			CRDNUM3: '',
			CRDSTS3: '',
			CRDSEL4: '',
			CRDSTP4: '',
			ACCTNO4: '',
			CRDNUM4: '',
			CRDSTS4: '',
			CRDSEL5: '',
			CRDSTP5: '',
			ACCTNO5: '',
			CRDNUM5: '',
			CRDSTS5: '',
			CRDSEL6: '',
			CRDSTP6: '',
			ACCTNO6: '',
			CRDNUM6: '',
			CRDSTS6: '',
			CRDSEL7: '',
			CRDSTP7: '',
			ACCTNO7: '',
			CRDNUM7: '',
			CRDSTS7: '',
			INFOMSG: '',
			ERRMSG: ''
		});
	}

	getMapName(): string {
		return "COCRDLI_CCRDLIA";
	}

	getForm(): FormGroup {
		return this.COCRDLI_CCRDLIA_FORM;
	}

}
