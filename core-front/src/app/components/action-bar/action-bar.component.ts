import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
    selector: 'app-action-bar',
    standalone: true,
    imports: [
        RouterLink
    ],
    templateUrl: './action-bar.component.html',
    styleUrl: './action-bar.component.css'
})
export class ActionBarComponent {
    items: ActionBarItem[] = [
        new ActionBarItem("Образовательные программы", "/admin/link1"),
        new ActionBarItem("Курсы и модули", "/admin/link2"),
        new ActionBarItem("Статистика", "/admin/link3")
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
