import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TextFieldComponent } from '../text-field/text-field.component';
import { ButtonComponent } from '../button/button.component';

@Component({
    selector: 'app-login-form',
    standalone: true,
    imports: [FormsModule, TextFieldComponent, ButtonComponent],
    templateUrl: './login-form.component.html',
    styleUrl: './login-form.component.css'
})
export class LoginFormComponent {
    login = "";
    password = "";

    onLogin() {
        console.log("Login " + this.login + " " + this.password)
    }

    click() {
        console.log("CLICK!!!")
    }

    setLogin(login: string) {
        this.login = login;
    }

    setPassword(password: string) {
        this.password = password;
    }
}
