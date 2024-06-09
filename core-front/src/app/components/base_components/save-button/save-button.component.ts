import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
    selector: 'app-save-button',
    standalone: true,
    imports: [],
    templateUrl: './save-button.component.html',
    styleUrl: './save-button.component.css'
})
export class SaveButtonComponent {
    @Output() clickEvent: EventEmitter<void> = new EventEmitter<void>();

    onClick() {
        this.clickEvent.emit();
    }
}
