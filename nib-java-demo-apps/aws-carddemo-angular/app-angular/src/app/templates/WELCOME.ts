import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule} from '@angular/forms';
import { AppService } from '../app.service';
import { BmsMap } from '../app.bmsmap';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

@Component({
	selector: 'app-WELCOME',
	templateUrl: './WELCOME.html',
	standalone: true,
	imports: [CommonModule, FormsModule],
	schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WELCOME implements AfterViewInit, BmsMap {

	WELCOME_FORM!: FormGroup;

	constructor(
		public appService: AppService,
		public formBuilder: FormBuilder,
	) { }

	ngOnInit() {
		this.WELCOME_FORM = this.formBuilder.group({});
	}

	ngAfterViewInit() {
		this.appService.populateConnectionData();
	}

	getMapName(): string {
		return "WELCOME";
	}

	getForm(): FormGroup {
		return this.WELCOME_FORM;
	}

	setText(fieldName: string): string {
		return this.WELCOME_FORM.get(fieldName)?.value ?? '';
	}

	setlastOrderFields(fields: any): void {
	}

	getlastOrderFields(): any {
		return null;
	}

}
