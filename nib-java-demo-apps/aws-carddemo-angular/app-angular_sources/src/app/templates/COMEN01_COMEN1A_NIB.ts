import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BmsBaseComponent } from './BmsBaseComponent';

@Component({
	selector: 'app-COMEN01-COMEN1A-NIB',
	templateUrl: './COMEN01_COMEN1A_NIB.html',
	standalone: true,
	imports: [CommonModule, FormsModule, ReactiveFormsModule ],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class COMEN01_COMEN1A_NIB extends BmsBaseComponent implements BmsMap {

	COMEN01_COMEN1A_NIB_FORM = this.formBuilder.group({
		OPTION: ''
	});

	constructor(
		appService: AppService,
		formBuilder: FormBuilder,
	) {
		super(appService, formBuilder);
	}

	getMapName(): string {
		return "COMEN01_COMEN1A_NIB";
	}

	getForm(): FormGroup {
		return this.COMEN01_COMEN1A_NIB_FORM;
	}

	menuMouseOver(option: string, value: string) {
		const menuOpt = document.getElementById(option);
		if (menuOpt){
			menuOpt.style['color'] = '#d4e8fc';
		}
		this.COMEN01_COMEN1A_NIB_FORM.get("OPTION")?.setValue(value);
	}

	menuMouseOut(option: string) {
		const menuOpt = document.getElementById(option);
		if (menuOpt){
			menuOpt.style['color'] = '#9bc2e8';
		}
	}

}
