import { Component } from '@angular/core';
import { Subscription } from "rxjs";
import { CommonModule } from '@angular/common';
import { Notification } from '../../../services/notification/notification';
import { NotificationComponent } from './notification/notification.component';
import { NotificationService } from '../../../services/notification/notification.service';

@Component({
    selector: 'app-notification-list',
    standalone: true,
    imports: [CommonModule, NotificationComponent],
    templateUrl: './notification-list.component.html',
    styleUrl: './notification-list.component.css'
})
export class NotificationListComponent {
    notifications: Notification[] = [];
    subscription: Subscription | undefined = undefined

    constructor(private notification: NotificationService) { }

    ngOnInit() {
        this.subscription = this.notification.getObservable().subscribe(n => this.addNotification(n))
    }

    ngOnDestroy() {
        this.subscription?.unsubscribe();
    }

    close(notification: Notification) {
        this.notifications = this.notifications.filter(notif => notif.id !== notification.id);
    }

    private addNotification(notification: Notification) {
        this.notifications.push(notification);
        setTimeout(() => this.close(notification), notification.duration);
    }
}
