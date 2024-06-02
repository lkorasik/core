import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TextFieldComponent } from '../text-field/text-field.component';

@Component({
    selector: 'app-login-form',
    standalone: true,
    imports: [FormsModule, TextFieldComponent],
    templateUrl: './login-form.component.html',
    styleUrl: './login-form.component.css'
})
export class LoginFormComponent {
    login = "";
    password = "";

    onLogin() {
        console.log("Login " + this.login + " " + this.password)
    }

    setLogin(login: string) {
        this.login = login;
    }

    setPassword(password: string) {
        this.password = password;
    }
}
