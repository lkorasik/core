import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
    selector: 'app-grid-card',
    standalone: true,
    imports: [RouterLink],
    templateUrl: './grid-card.component.html',
    styleUrl: './grid-card.component.css'
})
export class GridCardComponent {
    @Input() title = "";
    @Input() badge = "";
    @Input() link = "";
    badgeClass = "grid_badge_hide";

    getBadgeClass() {
        if (this.badge === "") {
            return "grid_badge_hide"
        } else {
            return "grid_badge_show"
        }
    }
}
