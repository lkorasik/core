import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-text-field',
    standalone: true,
    imports: [FormsModule],
    templateUrl: './text-field.component.html',
    styleUrl: './text-field.component.css'
})
export class TextFieldComponent {
    @Input() title = ""
    @Input() placeholder = ""
    @Input() type = "text"

    @Output() fieldValue: EventEmitter<string> = new EventEmitter<string>();
    @Input() value = ""

    setFieldValue() {
        this.fieldValue.emit(this.value);
    }
}
