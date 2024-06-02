import { Component, EventEmitter, Output } from '@angular/core';

@Component({
    selector: 'app-form-selector',
    standalone: true,
    imports: [],
    templateUrl: './form-selector.component.html',
    styleUrl: './form-selector.component.css'
})
export class FormSelectorComponent {
    @Output() formSelectorEvent = new EventEmitter<FormSelector>();

    FormSelector = FormSelector

    handle(type: FormSelector) {
        this.formSelectorEvent.emit(type);
    }
}

export enum FormSelector {
    LOGIN,
    REGISTRATION
}