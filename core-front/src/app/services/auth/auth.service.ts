import { Injectable } from '@angular/core';
import { LoginDTO } from './login.dto';
import { HttpClient } from '@angular/common/http';
import { AccessTokenDto } from './access-token.dto';
import { RegistrationDTO } from './registration.dto';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    constructor(private client: HttpClient) { }

    login(name: string, password: string) {
        let login = new LoginDTO(name, password);
        let token = this.client.post<AccessTokenDto>("/api/authentication/login", login).subscribe(
            response => sessionStorage.setItem("token", response.accessToken),
            error => console.log(error)
        );
        return token;
    }

    register(token: string, password: string, passwordAgain: string) {
        let register = new RegistrationDTO(token, password, passwordAgain);
        return this.client.post<AccessTokenDto>("/api/authentication/register", register).subscribe(x => console.log(x));
    }
}
