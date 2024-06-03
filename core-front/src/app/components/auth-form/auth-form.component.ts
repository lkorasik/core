import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TextFieldComponent } from '../text-field/text-field.component';
import { ButtonComponent } from '../button/button.component';
import { FormSelector, FormSelectorComponent } from '../form-selector/form-selector.component';
import { AuthService } from '../../services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-login-form',
    standalone: true,
    imports: [FormsModule, TextFieldComponent, ButtonComponent, FormSelectorComponent],
    templateUrl: './auth-form.component.html',
    styleUrl: './auth-form.component.css'
})
export class AuthFormComponent {
    FormSelector = FormSelector;

    login = "";
    password = "";
    passwordAgain = "";
    type = FormSelector.LOGIN;

    buttonLabel = "Войти";

    constructor(private service: AuthService, private router: Router) {}

    onClick() {
        console.log("Login " + this.login + " " + this.password)

        this.service.login(this.login, this.password);

        this.router.navigate(["administrator/educational_program"]);
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
