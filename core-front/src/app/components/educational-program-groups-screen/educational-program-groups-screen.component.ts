import { Component } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { TitleComponent } from '../title/title.component';
import { ProgramService } from '../../services/program/program.service';
import { ToolbarComponent } from '../toolbar/toolbar.component';
import { CloseButtonComponent } from '../close-button/close-button.component';
import { EditButtonComponent } from '../edit-button/edit-button.component';
import { AddButtonComponent } from '../add-button/add-button.component';
import { ProgramDTO } from '../../services/program/program.dto';
import { GridCard, GridComponent } from '../grid/grid.component';
import { GroupService } from '../../services/group/group.service';
import { GroupDto } from '../../services/group/group.dto';
import { GetGropupDTO } from '../../services/group/getGroup.dto';

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
    groups: GroupDto[] = []

    constructor(
        public route: ActivatedRoute,
        private router: Router,
        private programService: ProgramService,
        private groupService: GroupService
    ) {
        route.params.subscribe(x => {
            this.id = x['id']
            localStorage.setItem('programId', this.id);
        });
        programService.getEducationalProgramById({ id: this.id }).subscribe(x => this.name = x.title);
        const request = new GetGropupDTO(this.id);
        groupService.getGroupsForProgram(request).subscribe(x => this.groups = x);
    }

    getGroupCards() {
        return this.groups.map(x => new GridCard(x.number, '/administrator/group/' + x.id));
    }

    onClick() {
        this.router.navigate(["administrator/educational_program/edit/" + this.id])
    }
}
