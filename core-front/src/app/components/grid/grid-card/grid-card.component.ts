import { Component, Input } from '@angular/core';

@Component({
    selector: 'app-grid-card',
    standalone: true,
    imports: [],
    templateUrl: './grid-card.component.html',
    styleUrl: './grid-card.component.css'
})
export class GridCardComponent {
    @Input() title = "";
    @Input() badge = "";
    badgeClass = "grid_badge_hide";

    getBadgeClass() {
        if (this.badge === "") {
            return "grid_badge_hide"
        } else {
            return "grid_badge_show"
        }
    }
}
