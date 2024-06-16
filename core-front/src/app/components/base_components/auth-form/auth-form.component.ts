import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { TextFieldComponent } from '../text-field/text-field.component';
import { ButtonComponent } from '../button/button.component';
import { FormSelector, FormSelectorComponent } from '../form-selector/form-selector.component';
import { AuthService } from '../../../services/auth/auth.service';
import { NotificationService } from '../../../services/notification/notification.service';
import { AccessTokenDto } from '../../../services/auth/access-token.dto';
import { Callback } from '../../../services/callback';

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

    constructor(
        private service: AuthService, 
        private router: Router, 
        private notificationService: NotificationService
    ) {}

    onClick() {
        if (this.type == FormSelector.LOGIN) {
            let callback: Callback<AccessTokenDto> = new Callback<AccessTokenDto>((x: AccessTokenDto) => {
                this.notificationService.info("Вход", "Вы вошли в систему");
                this.router.navigate(["administrator/educational_program"]);
            }, (_: any) => this.notificationService.error("Вход", "Произошла ошибка"));

            this.service.login(callback, this.login, this.password);
        } else {
            let callback: Callback<AccessTokenDto> = new Callback<AccessTokenDto>((x: AccessTokenDto) => {
                this.notificationService.info("Регистрация", "Вы зарегистрировались");
                this.router.navigate(["administrator/educational_program"]);   
            }, (_: any) => this.notificationService.error("Регистрация", "Произошла ошибка"));

            this.service.register(callback, this.login, this.password, this.passwordAgain);
        }
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
