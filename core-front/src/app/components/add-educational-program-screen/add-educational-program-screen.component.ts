import { Component } from '@angular/core';
import { TitleComponent } from '../title/title.component';
import { TextFieldComponent } from '../text-field/text-field.component';

@Component({
    selector: 'app-add-educational-program-screen',
    standalone: true,
    imports: [TitleComponent, TextFieldComponent],
    templateUrl: './add-educational-program-screen.component.html',
    styleUrl: './add-educational-program-screen.component.css'
})
export class AddEducationalProgramScreenComponent {

}
