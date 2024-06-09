import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
    selector: 'app-download-button',
    standalone: true,
    imports: [],
    templateUrl: './download-button.component.html',
    styleUrl: './download-button.component.css'
})
export class DownloadButtonComponent {
    @Output() onClick: EventEmitter<void> = new EventEmitter<void>();

    onDownloadClick() {
        this.onClick.emit();
    }
}
