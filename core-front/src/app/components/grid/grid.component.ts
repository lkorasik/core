import { Component } from '@angular/core';
import { GridCardComponent } from './grid-card/grid-card.component';

@Component({
    selector: 'app-grid',
    standalone: true,
    imports: [
        GridCardComponent
    ],
    templateUrl: './grid.component.html',
    styleUrl: './grid.component.css'
})
export class GridComponent {
    cards: string[] = [];
}
