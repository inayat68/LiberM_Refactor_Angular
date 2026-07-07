import { FormGroup } from "@angular/forms";

export declare interface BmsMap {
    getMapName(): string;
    getForm(): FormGroup;
    setText(fieldName: string): string;
    setlastOrderFields(fields: any): void;
    getlastOrderFields(): any;
}
