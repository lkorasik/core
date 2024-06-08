import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationType } from '../../../services/notification/notificationsType';

@Component({
    selector: 'app-notification',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './notification.component.html',
    styleUrl: './notification.component.css'
})
export class NotificationComponent {
    @Input() title: string = "";
    @Input() type: NotificationType = NotificationType.info;

    getClass() {
        if (this.type == NotificationType.error) {
            return 'error';
        }
        return 'info';  
    }
}
