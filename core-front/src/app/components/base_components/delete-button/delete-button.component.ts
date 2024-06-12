import { Component, EventEmitter, Output } from '@angular/core';

@Component({
    selector: 'app-delete-button',
    standalone: true,
    imports: [],
    templateUrl: './delete-button.component.html',
    styleUrl: './delete-button.component.css'
})
export class DeleteButtonComponent {
    @Output() clickEvent: EventEmitter<void> = new EventEmitter<void>();

    onClick() {
        this.clickEvent.emit();
    }
}
