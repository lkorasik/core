import { Injectable } from '@angular/core';
import { LoginDTO } from './login.dto';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    constructor(private client: HttpClient) { }


    hello(name: string, password: string) {
        console.log("hello")

        let user = new LoginDTO(name, password);

        console.log(user)

        let response = this.client.post("/api/authentication/login", user).subscribe(x => console.log(x));
    }
}
