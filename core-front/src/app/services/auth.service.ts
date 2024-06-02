import { Injectable } from '@angular/core';
import { LoginDTO } from './login.dto';
import { HttpClient } from '@angular/common/http';
import { AccessTokenDto } from './access-token.dto';
import { RegistrationDTO } from './registration.dto';
import { Observable, catchError } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    constructor(private client: HttpClient) { }

    login(name: string, password: string) {
        let login = new LoginDTO(name, password);
        return this.client.post<AccessTokenDto>("/api/authentication/login", login).subscribe(x => console.log(x));
    }

    register(token: string, password: string, passwordAgain: string) {
        let register = new RegistrationDTO(token, password, passwordAgain);
        return this.client.post<AccessTokenDto>("/api/authentication/register", register).subscribe(x => console.log(x));
    }
}
