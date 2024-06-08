import { Component } from '@angular/core';
import {Location} from '@angular/common';

@Component({
    selector: 'app-close-button',
    standalone: true,
    imports: [],
    templateUrl: './close-button.component.html',
    styleUrl: './close-button.component.css'
})
export class CloseButtonComponent {
    constructor(private location: Location) {}
    
    onClick() {
        console.log("S")
        this.location.back()
    }
}
