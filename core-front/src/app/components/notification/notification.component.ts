import { Component } from '@angular/core';
import { Subscription } from "rxjs";
import { NotificationService, NotificationType } from '../../services/notification.service';
import { Notification } from '../../services/notification.service';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-notification',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './notification.component.html',
    styleUrl: './notification.component.css'
})
export class NotificationComponent {
    notifications: Notification[] = [];
    // private _subscription: Subscription;
    subscription: Subscription | undefined = undefined

    constructor(private notification: NotificationService) { }

    private _addNotification(notification: Notification) {
        this.notifications.push(notification);

        if (notification.timeout !== 0) {
            setTimeout(() => this.close(notification), notification.timeout);

        }
    }

    ngOnInit() {
        this.subscription = this.notification.getObservable().subscribe(n => this._addNotification(n))
    }

    ngOnDestroy() {
        this.subscription?.unsubscribe();
    }

    close(notification: Notification) {
        this.notifications = this.notifications.filter(notif => notif.id !== notification.id);
    }


    className(notification: Notification): string {

        let style: string;

        switch (notification.type) {

            case NotificationType.success:
                style = 'success';
                break;

            case NotificationType.warning:
                style = 'warning';
                break;

            case NotificationType.error:
                style = 'error';
                break;

            default:
                style = 'info';
                break;
        }

        return style;
    }

}
