import { Component } from '@angular/core';
import { TitleComponent } from '../title/title.component';
import { TextFieldComponent } from '../text-field/text-field.component';
import { ToolbarComponent } from '../toolbar/toolbar.component';
import { SaveButtonComponent } from '../save-button/save-button.component';
import { CloseButtonComponent } from '../close-button/close-button.component';

@Component({
    selector: 'app-add-educational-program-screen',
    standalone: true,
    imports: [TitleComponent, TextFieldComponent, ToolbarComponent, SaveButtonComponent, CloseButtonComponent],
    templateUrl: './add-educational-program-screen.component.html',
    styleUrl: './add-educational-program-screen.component.css'
})
export class AddEducationalProgramScreenComponent {

}
