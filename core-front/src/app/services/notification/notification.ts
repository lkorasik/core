import { NotificationDuration } from "./notificationDuration";
import { NotificationType } from "./notificationsType";

export class Notification {
    id: number;
    type: NotificationType;
    title: string;
    message: string;    
    duration: NotificationDuration

    constructor(id: number, type: NotificationType, title: string, message: string, duration: NotificationDuration) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.message = message;
        this.duration = duration;
    }
}