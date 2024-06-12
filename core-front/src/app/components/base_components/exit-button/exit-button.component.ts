import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'app-exit-button',
    standalone: true,
    imports: [],
    templateUrl: './exit-button.component.html',
    styleUrl: './exit-button.component.css'
})
export class ExitButtonComponent {
    constructor(private router: Router) {}

    onClick() {
        sessionStorage.clear()
        this.router.navigate(["/"])
    }
}
