import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ButtonComponent } from '../button/button.component';
import { TextFieldComponent } from '../text-field/text-field.component';

@Component({
    selector: 'app-dialog',
    standalone: true,
    imports: [
        ButtonComponent,
        TextFieldComponent
    ],
    templateUrl: './dialog.component.html',
    styleUrl: './dialog.component.css'
})
export class DialogComponent {
    @Input() title: string = "";
    @Output() onLeftButtonClick: EventEmitter<void> = new EventEmitter<void>();
    @Output() onRightButtonClick: EventEmitter<void> = new EventEmitter<void>();
    @Input() leftButtonTitle: string = ""
    @Input() rightButtonTitle: string = ""
    @Input() isOpen = false;
    
    getLeftButtonTitle() {
        if (this.leftButtonTitle == "") {
            return "Закрыть";
        }
        return this.leftButtonTitle;
    }

    getRightButtonTitle() {
        if (this.rightButtonTitle == "") {
            return "Сохранить";
        }
        return this.rightButtonTitle;
    }

    onCloseClick() {
        this.onLeftButtonClick.emit();
    }

    onActionClick() {
        this.onRightButtonClick.emit();
    }
}
