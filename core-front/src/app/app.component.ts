import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthFormComponent } from './components/base_components/auth-form/auth-form.component';
import { NotificationListComponent } from './components/base_components/notification-list/notification-list.component';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [AuthFormComponent, RouterOutlet, NotificationListComponent],
    templateUrl: './app.component.html',
    styleUrl: './app.component.css'
})
export class AppComponent {
}
