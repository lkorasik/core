import { Component } from '@angular/core';
import { AuthFormComponent } from '../../base_components/auth-form/auth-form.component';

@Component({
    selector: 'app-welcome-screen',
    standalone: true,
    imports: [AuthFormComponent],
    templateUrl: './welcome-screen.component.html',
    styleUrl: './welcome-screen.component.css'
})
export class WelcomeScreenComponent {
}
