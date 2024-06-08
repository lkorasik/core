import { Component } from '@angular/core';
import { AuthFormComponent } from './components/auth-form/auth-form.component';
import { RouterOutlet } from '@angular/router';
import { NotificationListComponent } from './components/notification-list/notification-list.component';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [AuthFormComponent, RouterOutlet, NotificationListComponent],
    templateUrl: './app.component.html',
    styleUrl: './app.component.css'
})
export class AppComponent {
}
