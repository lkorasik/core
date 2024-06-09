import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ActionBarComponent } from '../../base_components/action-bar/action-bar.component';
import { ContainerComponent } from '../../base_components/container/container.component';

@Component({
    selector: 'app-admin-main-screen',
    standalone: true,
    imports: [ActionBarComponent, RouterOutlet, ContainerComponent],
    templateUrl: './admin-main-screen.component.html',
    styleUrl: './admin-main-screen.component.css'
})
export class AdminMainScreenComponent {
}
