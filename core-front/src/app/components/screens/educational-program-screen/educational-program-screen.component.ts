import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { GridCard, GridComponent } from '../../base_components/grid/grid.component';
import { TitleComponent } from '../../base_components/title/title.component';
import { AddButtonComponent } from '../../base_components/add-button/add-button.component';
import { NotificationListComponent } from '../../base_components/notification-list/notification-list.component';
import { ProgramDTO } from '../../../services/program/program.dto';
import { ProgramService } from '../../../services/program/program.service';
import { NotificationService } from '../../../services/notification/notification.service';

@Component({
    selector: 'app-educational-program-screen',
    standalone: true,
    imports: [GridComponent, RouterOutlet, TitleComponent, AddButtonComponent, NotificationListComponent],
    templateUrl: './educational-program-screen.component.html',
    styleUrl: './educational-program-screen.component.css'
})
export class EducationalProgramScreenComponent {
    programs: ProgramDTO[] = [{
        id: "1",
        name: "Образовательные программы"
    }];
    
    constructor(private programService: ProgramService, private notificationService: NotificationService, private router: Router) {
        programService.getAllPrograms().subscribe(x => this.programs = x);
    }

    getGridCards() {
        return this.programs.map(x => new GridCard(x.name, "/administrator/educational_program/" + x.id));
    }

    onAddButtonClick() {
        this.router.navigate(["administrator/educational_program/add"])
    }
}
