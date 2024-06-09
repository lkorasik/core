import { Component } from '@angular/core';
import {Location} from '@angular/common';

@Component({
    selector: 'app-not-found-screen',
    standalone: true,
    imports: [],
    templateUrl: './not-found-screen.component.html',
    styleUrl: './not-found-screen.component.css'
})
export class NotFoundScreenComponent {
    constructor(private location: Location) { }

    onClick() {
        this.location.back()
    }
}
