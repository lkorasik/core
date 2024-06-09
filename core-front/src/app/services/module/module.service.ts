import { Injectable } from '@angular/core';
import { AuthorizedHttpClient } from '../authorizedHttpClient';
import { ModuleDTO } from './module.dto';
import { CreateModuleDto } from './createModule.dto';
import { GetModuleById, ModuleWithCoursesDTO } from './dtos';
import { HttpParams } from '@angular/common/http';

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

    public getModuleById(getModuleById: GetModuleById) {
        let params = new HttpParams().set("id", getModuleById.id);
        return this.authorizedClient.get<ModuleWithCoursesDTO>("api/modules/module", params);
    }
}
