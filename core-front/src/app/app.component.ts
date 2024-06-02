import { Component } from '@angular/core';
import { AuthFormComponent } from './components/auth-form/auth-form.component';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [AuthFormComponent],
    templateUrl: './app.component.html',
    styleUrl: './app.component.css'
})
export class AppComponent {
}
