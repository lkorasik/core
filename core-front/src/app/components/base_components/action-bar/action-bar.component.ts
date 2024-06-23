import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ExitButtonComponent } from '../exit-button/exit-button.component';

@Component({
    selector: 'app-action-bar',
    standalone: true,
    imports: [
        RouterLink,
        ExitButtonComponent
    ],
    templateUrl: './action-bar.component.html',
    styleUrl: './action-bar.component.css'
})
export class ActionBarComponent {
    items: ActionBarItem[] = [
        new ActionBarItem("Образовательные программы", "/administrator/educational_program"),
        new ActionBarItem("Курсы и модули", "/administrator/module"),
        new ActionBarItem("Статистика", "/administrator/link3")
    ]
}

export class ActionBarItem {
    title: string;
    link: string;

    constructor(title: string, link: string) { 
        this.title = title;
        this.link = link;
    }
}
