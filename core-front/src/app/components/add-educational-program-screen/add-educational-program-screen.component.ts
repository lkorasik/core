import { Component, Output } from '@angular/core';
import { TitleComponent } from '../title/title.component';
import { TextFieldComponent } from '../text-field/text-field.component';
import { ToolbarComponent } from '../toolbar/toolbar.component';
import { SaveButtonComponent } from '../save-button/save-button.component';
import { CloseButtonComponent } from '../close-button/close-button.component';
import { ProgramService } from '../../services/program/program.service';
import { CreateProgramDTO } from '../../services/program/createProgram.dto';
import {Location} from '@angular/common';

@Component({
    selector: 'app-add-educational-program-screen',
    standalone: true,
    imports: [TitleComponent, TextFieldComponent, ToolbarComponent, SaveButtonComponent, CloseButtonComponent],
    templateUrl: './add-educational-program-screen.component.html',
    styleUrl: './add-educational-program-screen.component.css'
})
export class AddEducationalProgramScreenComponent {
    name: string = "";
    trainingDirection: string = "";

    constructor(private programService: ProgramService, private location: Location) {}
    
    setName(name: string) {
        this.name = name;
    }

    setTrainingDirection(trainingDirection: string) {
        this.trainingDirection = trainingDirection;
    }

    onSave() {
        const request = new CreateProgramDTO(this.name, this.trainingDirection);
        this.programService.createEducationalProgram(request).subscribe(x => console.log(x));
        this.location.back()
    }
}
