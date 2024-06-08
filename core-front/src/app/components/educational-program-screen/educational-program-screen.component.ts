import { Component } from '@angular/core';
import { ProgramService } from '../../services/program/program.service';
import { ProgramDTO } from '../../services/program/program.dto';
import { GridCard, GridComponent } from '../grid/grid.component';
import { Router, RouterOutlet } from '@angular/router';
import { TitleComponent } from '../title/title.component';
import { AddButtonComponent } from '../add-button/add-button.component';
import { NotificationService } from '../../services/notification/notification.service';
import { NotificationListComponent } from '../notification-list/notification-list.component';

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
