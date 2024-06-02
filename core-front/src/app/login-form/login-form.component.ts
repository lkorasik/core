import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TextFieldComponent } from '../text-field/text-field.component';
import { ButtonComponent } from '../button/button.component';
import { FormSelectorComponent } from '../form-selector/form-selector.component';

@Component({
    selector: 'app-login-form',
    standalone: true,
    imports: [FormsModule, TextFieldComponent, ButtonComponent, FormSelectorComponent],
    templateUrl: './login-form.component.html',
    styleUrl: './login-form.component.css'
})
export class LoginFormComponent {
    login = "";
    password = "";

    onClick() {
        console.log("Login " + this.login + " " + this.password)
    }

    setLogin(login: string) {
        this.login = login;
    }

    setPassword(password: string) {
        this.password = password;
    }
}
