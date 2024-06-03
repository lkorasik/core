import { Component } from '@angular/core';
import { ProgramService } from '../../services/program/program.service';
import { ProgramDTO } from '../../services/program/program.dto';
import { GridComponent } from '../grid/grid.component';

@Component({
    selector: 'app-educational-program-screen',
    standalone: true,
    imports: [
        GridComponent
    ],
    templateUrl: './educational-program-screen.component.html',
    styleUrl: './educational-program-screen.component.css'
})
export class EducationalProgramScreenComponent {
    programs: ProgramDTO[] = [];
    
    constructor(private programService: ProgramService) {
        programService.getAllPrograms().subscribe(x => this.programs = x);
    }
}
