import { Injectable } from '@angular/core';
import { AuthorizedHttpClient } from '../authorizedHttpClient';
import { ModuleDTO } from './module.dto';
import { CreateModuleDto } from './createModule.dto';

@Injectable({
    providedIn: 'root'
})
export class ModuleService {
    constructor(private authorizedClient: AuthorizedHttpClient) { }
    
    public getAllModules() {
        return this.authorizedClient.get<ModuleDTO[]>("api/modules/all");
    }

    public createModule(createModuleRequest: CreateModuleDto) {
        return this.authorizedClient.post("api/modules/create", createModuleRequest);
    }
}
