import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TextFieldComponent } from '../text-field/text-field.component';
import { ButtonComponent } from '../button/button.component';
import { FormSelector, FormSelectorComponent } from '../form-selector/form-selector.component';

@Component({
    selector: 'app-login-form',
    standalone: true,
    imports: [FormsModule, TextFieldComponent, ButtonComponent, FormSelectorComponent],
    templateUrl: './login-form.component.html',
    styleUrl: './login-form.component.css'
})
export class LoginFormComponent {
    FormSelector = FormSelector;

    login = "";
    password = "";
    passwordAgain = "";
    type = FormSelector.LOGIN;

    buttonLabel = "Войти";

    onClick() {
        console.log("Login " + this.login + " " + this.password)
    }

    setLogin(login: string) {
        this.login = login;
    }

    setPassword(password: string) {
        this.password = password;
    }

    setPasswordAgain(passwordAgain: string) {
        this.passwordAgain = passwordAgain;
    }

    setForm(type: FormSelector) {
        this.type = type;
        switch(type) {
            case FormSelector.LOGIN:
                this.buttonLabel = "Войти";
                break;
            case FormSelector.REGISTRATION:
                this.buttonLabel = "Зарегистрироваться";
                break;
        }
    }
}
