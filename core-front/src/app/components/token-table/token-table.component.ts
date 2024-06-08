import { Component, Input } from '@angular/core';

@Component({
    selector: 'app-token-table',
    standalone: true,
    imports: [],
    templateUrl: './token-table.component.html',
    styleUrl: './token-table.component.css'
})
export class TokenTableComponent {
    @Input() tokens: string[][] = []
}
