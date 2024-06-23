import { Injectable } from '@angular/core';
import { LoginDTO } from './login.dto';
import { HttpClient } from '@angular/common/http';
import { AccessTokenDto } from './access-token.dto';
import { RegistrationDTO } from './registration.dto';
import { StorageService } from '../storage.service';
import { Observer } from 'rxjs';
import { Callback } from '../callback';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    constructor(private client: HttpClient, private storageService: StorageService) { }

    login(callback: Callback<AccessTokenDto>, name: string, password: string) {
        let login = new LoginDTO(name, password);
        let token = this.client
            .post<AccessTokenDto>("/api/authentication/login", login)
            .subscribe(this.wrapCallback(callback));
        return token;
    }

    register(callback: Callback<AccessTokenDto>, token: string, password: string, passwordAgain: string) {
        let register = new RegistrationDTO(token, password, passwordAgain);
        return this.client
            .post<AccessTokenDto>("/api/authentication/register", register)
            .subscribe(this.wrapCallback(callback));
    }

    private wrapCallback(callback: Callback<AccessTokenDto>): Observer<AccessTokenDto> {
        return {
            next: (dto: AccessTokenDto) => {
                this.storageService.saveAuthorizationToken(dto.accessToken);
                callback?.success?.(dto);
            },
            error: (err: any) => callback?.error?.(err),
            complete: () => { }
        }
    }
}