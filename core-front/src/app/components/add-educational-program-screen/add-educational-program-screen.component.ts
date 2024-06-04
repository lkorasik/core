import { Component } from '@angular/core';
import { TitleComponent } from '../title/title.component';
import { TextFieldComponent } from '../text-field/text-field.component';
import { ToolbarComponent } from '../toolbar/toolbar.component';

@Component({
    selector: 'app-add-educational-program-screen',
    standalone: true,
    imports: [TitleComponent, TextFieldComponent, ToolbarComponent],
    templateUrl: './add-educational-program-screen.component.html',
    styleUrl: './add-educational-program-screen.component.css'
})
export class AddEducationalProgramScreenComponent {

}
