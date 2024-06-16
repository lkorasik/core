import { Component } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { TitleComponent } from '../../base_components/title/title.component';
import { ToolbarComponent } from '../../base_components/toolbar/toolbar.component';
import { CloseButtonComponent } from '../../base_components/close-button/close-button.component';
import { EditButtonComponent } from '../../base_components/edit-button/edit-button.component';
import { GridCard, GridComponent } from '../../base_components/grid/grid.component';
import { AddButtonComponent } from '../../base_components/add-button/add-button.component';
import { ProgramDTO } from '../../../services/program/program.dto';
import { GroupDto } from '../../../services/group/group.dto';
import { ProgramService } from '../../../services/program/program.service';
import { GroupService } from '../../../services/group/group.service';
import { GetGropupDTO } from '../../../services/group/getGroup.dto';
import { GroupDTO } from '../../../services/program/group.dto';

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
    groups: GroupDTO[] = []

    constructor(
        public route: ActivatedRoute,
        private router: Router,
        private programService: ProgramService,
        private groupService: GroupService
    ) {
        route.params.subscribe(x => {
            this.id = x['id']
            sessionStorage.setItem('programId', this.id);
        });
        programService.getEducationalProgramById({ id: this.id }).subscribe(x => {
            this.name = x.title;
            this.groups = x.groups;
        });
    }

    getGroupCards() {
        return this.groups.map(x => new GridCard(x.number, '/administrator/group/' + x.id));
    }

    onClick() {
        sessionStorage.setItem("programId", this.id);
        this.router.navigate(["administrator/educational_program/edit/" + this.id])
    }

    onAddButtonClick() {
        this.router.navigate(["administrator/group/add"])
    }
}
