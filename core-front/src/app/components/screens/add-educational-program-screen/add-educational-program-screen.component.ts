import { Component, Output } from '@angular/core';
import {Location} from '@angular/common';
import { TitleComponent } from '../../base_components/title/title.component';
import { TextFieldComponent } from '../../base_components/text-field/text-field.component';
import { ToolbarComponent } from '../../base_components/toolbar/toolbar.component';
import { SaveButtonComponent } from '../../base_components/save-button/save-button.component';
import { CloseButtonComponent } from '../../base_components/close-button/close-button.component';
import { ProgramService } from '../../../services/program/program.service';
import { CreateProgramDTO } from '../../../services/program/createProgram.dto';

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
