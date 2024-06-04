import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TitleComponent } from '../title/title.component';
import { ProgramService } from '../../services/program/program.service';

@Component({
    selector: 'app-educational-program-groups-screen',
    standalone: true,
    imports: [TitleComponent],
    templateUrl: './educational-program-groups-screen.component.html',
    styleUrl: './educational-program-groups-screen.component.css'
})
export class EducationalProgramGroupsScreenComponent {
    id: string = ""
    name: string = ""

    constructor(public route: ActivatedRoute, private programService: ProgramService) {
        route.params.subscribe(x => this.id = x['id']);
        programService.getEducationalProgramById({ id: this.id }).subscribe(x => this.name = x.title);
    }
}
