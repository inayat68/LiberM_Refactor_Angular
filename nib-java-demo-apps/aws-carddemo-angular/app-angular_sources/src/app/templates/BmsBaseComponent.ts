import { AfterViewInit, Directive } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AppService } from '../app.service';

@Directive()
export abstract class BmsBaseComponent implements AfterViewInit {
  // child components must provide a FormGroup
  protected abstract getForm(): FormGroup;
  protected abstract getMapName(): string;

  //Fields from the last order, still contain all the attributes
  protected lastOrderFields: any[] | null = null;
  protected lastOrderFieldsMap: Map<string, any> = new Map<string, any>();

  //Field owning the focus
  protected focusedFieldName: string | null = null;

  protected constructor(
    public appService: AppService,
    public formBuilder: FormBuilder,
  ) {}

  ngAfterViewInit(): void {
    // mark this component as active and populate its map
    this.appService.activeComponent = this;
    this.appService.populateMap(this.getMapName());
  }

  sendKey(keyPressed: string): boolean {
    const form = this.getForm();
    const fields: Array<{ name: string; cursor: boolean; row: number; column: number; text: string }> = [];
    //console.log('DBG>>> sending key this.focusedFieldName ->', this.focusedFieldName);
    Object.keys(form.controls).forEach(key => {
      let value = form.get(key)?.value ?? '';
      let cursorValue = false;
      let field = this.lastOrderFieldsMap.get(key);
      let isAskip: boolean = false;
      let isFset: boolean = false;
      let isProt: boolean = false;
      if (field){
        //console.log('DBG>>> cics field -> ', field);
        //console.log('DBG>>> cics field.text -> [', field.text, ']');
        //console.log('DBG>>> cics field.text.length -> [', field.text.length, ']');
        isAskip = field.askip;
        isFset = field.fset;
        isProt = field.prot;
        if (isProt && isFset){
          //console.log('DBG>>> cics field is prot/fset, forcing orginal text -> ', field);
          value = field.text;
        }
      }
      if (key === this.focusedFieldName){
        //console.log('DBG>>> sending key cursor should be on ->', key);
        cursorValue = true;
      }
      let row: number = 0;
      let column: number = 0;
 			const element = document.getElementById(key);
 			//console.log(element);
			if (element) {
			  let currentClass: string = element.className;
        //console.log('DBG>>> sending key ->', key, ' class -> ', currentClass);
			  if (currentClass) {
          const classes = currentClass.split(' ');
          for (const c of classes) {
            if (c.startsWith('line')) row = Number(c.substring(4));
            if (c.startsWith('col')) column = Number(c.substring(3));
          }
        }
      }
      fields.push({ name: key, cursor: cursorValue, row: row, column: column, text: String(value).toUpperCase() });
    });
    //console.info('Key pressed:', keyPressed);
    this.appService.handleFunctionKey(keyPressed, fields);
    return false;
  }

  setFocusField(fieldName: string): void {
    this.focusedFieldName = fieldName;
    //console.log('DBG>>> setFocusField ->', this.focusedFieldName);
  }

  unsetFocusField(fieldName: string): void {
    //Do nothing if we press the Enter key on the pad keys the selected key loosees focus,
    //so we just retain the last one selected
    //this.focusedFieldName = "";
    //console.log('DBG>>> unsetFocusField ->', fieldName);
  }

  setText(fieldName: string): string {
    const form = this.getForm();
    return this.getForm().get(fieldName)?.value ?? '';
  }

  setlastOrderFields(fields: any): void {
    this.lastOrderFields = fields;
    this.lastOrderFieldsMap = new Map<string, any>();
    //Create a dictionary
    for (const field of fields) {
      this.lastOrderFieldsMap.set(field.name, field);
    }
  }

  getlastOrderFields(): any {
    return this.lastOrderFields;
  }

}
