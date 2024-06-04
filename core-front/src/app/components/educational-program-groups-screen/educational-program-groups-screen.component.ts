import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TitleComponent } from '../title/title.component';
import { ProgramService } from '../../services/program/program.service';
import { ToolbarComponent } from '../toolbar/toolbar.component';
import { CloseButtonComponent } from '../close-button/close-button.component';
import { EditButtonComponent } from '../edit-button/edit-button.component';
import { GridCardComponent } from '../grid/grid-card/grid-card.component';
import { AddButtonComponent } from '../add-button/add-button.component';
import { ProgramDTO } from '../../services/program/program.dto';
import { GridCard, GridComponent } from '../grid/grid.component';

@Component({
    selector: 'app-educational-program-groups-screen',
    standalone: true,
    imports: [TitleComponent, ToolbarComponent, CloseButtonComponent, EditButtonComponent, GridComponent, AddButtonComponent],
    templateUrl: './educational-program-groups-screen.component.html',
    styleUrl: './educational-program-groups-screen.component.css'
})
export class EducationalProgramGroupsScreenComponent {
    id: string = ""
    name: string = ""
    programs: ProgramDTO[] = []

    constructor(public route: ActivatedRoute, private programService: ProgramService) {
        route.params.subscribe(x => this.id = x['id']);
        programService.getEducationalProgramById({ id: this.id }).subscribe(x => this.name = x.title);
    }

    getGridCards() {
        return [new GridCard(this.name, this.id)];
    }
}
