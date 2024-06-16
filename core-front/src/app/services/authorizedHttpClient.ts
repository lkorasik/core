import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { StorageService } from "./storage.service";

@Injectable({
    providedIn: 'root'
})
export class AuthorizedHttpClient {
    constructor(private client: HttpClient, private storageService: StorageService) {}

    get<T>(api: string, params?: HttpParams): Observable<T> {
        let headers = new HttpHeaders().append("Authorization", "Bearer " + this.storageService.loadAuthorizationToken());
        return this.client.get<T>(api, { headers, params });
    }

    post<T>(api: string, body: any, params?: HttpParams): Observable<T> {
        let headers = new HttpHeaders().append("Authorization", "Bearer " + this.storageService.loadAuthorizationToken());
        return this.client.post<T>(api, body, { headers, params });
    }
}