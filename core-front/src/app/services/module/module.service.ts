import { Injectable } from '@angular/core';
import { AuthorizedHttpClient } from '../authorizedHttpClient';
import { ModuleDTO } from './module.dto';

@Injectable({
    providedIn: 'root'
})
export class ModuleService {
    constructor(private authorizedClient: AuthorizedHttpClient) { }
    
    public getAllModules() {
        return this.authorizedClient.get<ModuleDTO[]>("api/modules/all");
    }
}
