import { Component } from '@angular/core';

@Component({
    selector: 'app-action-bar',
    standalone: true,
    imports: [],
    templateUrl: './action-bar.component.html',
    styleUrl: './action-bar.component.css'
})
export class ActionBarComponent {
    items: ActionBarItem[] = [
        new ActionBarItem("Образовательные программы", "/link1"),
        new ActionBarItem("Курсы и модули", "/link2"),
        new ActionBarItem("Статистика", "/link3")
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
