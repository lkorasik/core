import { Component } from '@angular/core';
import { ButtonComponent } from '../../base_components/button/button.component';
import { Location } from '@angular/common';

@Component({
    selector: 'app-not-found',
    standalone: true,
    imports: [ButtonComponent],
    templateUrl: './not-found-screen.component.html',
    styleUrl: './not-found-screen.component.css'
})
export class NotFoundScreenComponent {
    constructor(private location: Location) { }

    onBackClick() {
        this.location.back()
    }
}
