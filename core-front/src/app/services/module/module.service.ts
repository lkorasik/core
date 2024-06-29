import { Injectable } from '@angular/core';
import { AuthorizedHttpClient } from '../authorizedHttpClient';
import { ModuleDTO } from './module.dto';
import { CreateModuleDto, GetModuleById, ModuleWithCoursesDTO } from './dtos';
import { HttpParams } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class ModuleService {
    constructor(private authorizedClient: AuthorizedHttpClient) { }

    public getAllModules() {
        return this.authorizedClient.get<ModuleDTO[]>("api/module/all");
    }

    public createModule(createModuleRequest: CreateModuleDto) {
        return this.authorizedClient.post("api/module/create", createModuleRequest);
    }

    public getModuleById(getModuleById: GetModuleById) {
        let params = new HttpParams().set("id", getModuleById.id);
        return this.authorizedClient.get<ModuleWithCoursesDTO>("api/module/module", params);
    }
}
