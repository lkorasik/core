import { Component } from '@angular/core';
import { LoginFormComponent } from './login-form/login-form.component';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [LoginFormComponent],
    templateUrl: './app.component.html',
    styleUrl: './app.component.css'
})
export class AppComponent {
}
