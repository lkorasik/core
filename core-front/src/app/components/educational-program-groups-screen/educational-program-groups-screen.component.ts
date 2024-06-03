import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-educational-program-groups-screen',
    standalone: true,
    imports: [],
    templateUrl: './educational-program-groups-screen.component.html',
    styleUrl: './educational-program-groups-screen.component.css'
})
export class EducationalProgramGroupsScreenComponent {
    id: string = ""

    constructor(public route: ActivatedRoute) { 
        route.params.subscribe(x => this.id = x['id']);
    }
}
