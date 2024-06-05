import { Component } from '@angular/core';
import { ToolbarComponent } from '../toolbar/toolbar.component';
import { SaveButtonComponent } from '../save-button/save-button.component';
import { EditButtonComponent } from '../edit-button/edit-button.component';
import { TextFieldComponent } from '../text-field/text-field.component';
import { CloseButtonComponent } from '../close-button/close-button.component';

@Component({
    selector: 'app-group-screen',
    standalone: true,
    imports: [ToolbarComponent, SaveButtonComponent, EditButtonComponent, TextFieldComponent, CloseButtonComponent],
    templateUrl: './group-screen.component.html',
    styleUrl: './group-screen.component.css'
})
export class GroupScreenComponent {
    onSave() {

    }

    setNumber(number: string) {

    }

    setYear(year: string) {

    }
}
