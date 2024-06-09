import { Component, EventEmitter, Output } from '@angular/core';

@Component({
    selector: 'app-edit-button',
    standalone: true,
    imports: [],
    templateUrl: './edit-button.component.html',
    styleUrl: './edit-button.component.css'
})
export class EditButtonComponent {
    @Output() clickEvent: EventEmitter<void> = new EventEmitter<void>();

    onClick() {
        this.clickEvent.emit();
    }
}
