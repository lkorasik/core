import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Notification } from './notification';
import { NotificationType } from './notificationsType';
import { NotificationDuration } from './notificationDuration';

@Injectable({
    providedIn: 'root'
})
export class NotificationService {
    private subject = new Subject<Notification>();
    private idx = 0

    getObservable() {
        return this.subject.asObservable();
    }

    info(title: string, message: string) {
        let notification = this.buildNotification(NotificationType.info, title, message);
        this.subject.next(notification);
    }

    error(title: string, message: string) {
        let notification = this.buildNotification(NotificationType.error, title, message);
        this.subject.next(notification);
    }

    private buildNotification(type: NotificationType, title: string, message: string) {
        return new Notification(this.idx++, type, title, message, NotificationDuration.short);
    }
}
