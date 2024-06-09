import { Component, Input } from '@angular/core';
import { TitleComponent } from '../title/title.component';

@Component({
    selector: 'app-toolbar',
    standalone: true,
    imports: [TitleComponent],
    templateUrl: './toolbar.component.html',
    styleUrl: './toolbar.component.css'
})
export class ToolbarComponent {
    @Input() title = "";
}
