import { Injectable } from '@angular/core';
import { AuthorizedHttpClient } from '../authorizedHttpClient';
import { ModuleDTO } from './module.dto';
import { CreateModuleDto, GetModuleById, ModuleWithCoursesDTO } from './dtos';
import { HttpParams } from '@angular/common/http';
import { FullModuleDto } from './fullModule.dto';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class ModuleService {
    constructor(private authorizedClient: AuthorizedHttpClient) { }

    public getAllModules() {
        return this.authorizedClient.get<ModuleDTO[]>("api/module/all");
    }

    public createModule(createModuleRequest: CreateModuleDto) {
        return this.authorizedClient.post("api/module", createModuleRequest);
    }

    getAllModulesWithCourses(): Observable<FullModuleDto[]> {
        return this.authorizedClient.get<FullModuleDto[]>("api/module/allWithCourses");
    }

    public getModuleById(getModuleById: GetModuleById) {
        let params = new HttpParams().set("id", getModuleById.id);
        return this.authorizedClient.get<ModuleWithCoursesDTO>("api/module", params);
    }
}
