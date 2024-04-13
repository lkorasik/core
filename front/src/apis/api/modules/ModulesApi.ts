import {GetModulesDto} from "./GetModulesDto";
import {ModuleDto} from "./ModuleDto";
import {ApiBase} from "../../ApiBase/ApiBase";
import {DeleteModuleDto} from "./DeleteModuleDto";
import {CreateModuleDto} from "./CreateModuleDto";

export class ModulesApi extends ApiBase implements IModulesApi {
    public async getModulesByIds(getModulesRequest: GetModulesDto): Promise<ModuleDto[]> {
        return await this.post("modules", {}, {
            ...getModulesRequest
        });
    }
    public async getAllModules(): Promise<ModuleDto[]> {
        return await this.get("modules/all", {}, {});
    }

    public async deleteModuleById(deleteEducationalModuleRequest: DeleteModuleDto): Promise<void> {
        return await this.delete("modules/delete", {}, {
            ...deleteEducationalModuleRequest
        });
    }

    public async createModule(createModuleRequest: CreateModuleDto): Promise<void> {
        return await this.post("modules/create", {}, {
            ...createModuleRequest
        });
    }
}

export interface IModulesApi {
    getModulesByIds(getModulesRequest: GetModulesDto): Promise<ModuleDto[]>;
    getAllModules(): Promise<ModuleDto[]>
    deleteModuleById(deleteModuleRequest: DeleteModuleDto): Promise<void>
    createModule(createModuleRequest: CreateModuleDto): Promise<void>
}
