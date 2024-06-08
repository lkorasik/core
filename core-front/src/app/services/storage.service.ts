import { Injectable } from '@angular/core';

const TOKEN_KEY = 'token';

@Injectable({
    providedIn: 'root'
})
export class StorageService {
    saveAuthorizationToken(token: string) {
        localStorage.setItem(TOKEN_KEY, token);
    }

    loadAuthorizationToken() {
        return localStorage.getItem(TOKEN_KEY);
    }

    clearAuthorizationToken() {
        localStorage.removeItem(TOKEN_KEY);
    }
}
