import { Component } from '@angular/core';
import { ProgramService } from '../../services/program/program.service';
import { ProgramDTO } from '../../services/program/program.dto';
import { GridCard, GridComponent } from '../grid/grid.component';
import { RouterOutlet } from '@angular/router';
import { TitleComponent } from '../title/title.component';

@Component({
    selector: 'app-educational-program-screen',
    standalone: true,
    imports: [
        GridComponent,
        RouterOutlet,
        TitleComponent
    ],
    templateUrl: './educational-program-screen.component.html',
    styleUrl: './educational-program-screen.component.css'
})
export class EducationalProgramScreenComponent {
    programs: ProgramDTO[] = [];
    
    constructor(private programService: ProgramService) {
        programService.getAllPrograms().subscribe(x => this.programs = x);
    }

    getGridCards() {
        return this.programs.map(x => new GridCard(x.name, x.id));
    }
}
