import {GetModulesRequest} from "../dto/GetModulesRequest";
import {EducationalModule} from "../dto/EducationalModule";
import {ApiBase} from "../ApiBase/ApiBase";
import {DeleteEducationalModuleRequest} from "../dto/DeleteEducationalModuleRequest";
import {CreateEducationalModuleRequest} from "../dto/CreateEducationalModuleRequest";

export class EducationalModulesApi extends ApiBase implements IEducationalModulesApi {
    public async getModulesByIds(getModulesRequest: GetModulesRequest): Promise<EducationalModule[]> {
        return await this.post("educationalModules", {}, {
            ...getModulesRequest
        });
    }
    public async getAllModules(): Promise<EducationalModule[]> {
        return await this.get("educationalModules", {}, {});
    }

    public async deleteModuleById(deleteEducationalModuleRequest: DeleteEducationalModuleRequest): Promise<void> {
        return await this.delete("educationalModules/delete", {}, {
            ...deleteEducationalModuleRequest
        });
    }

    public async createModule(createModuleRequest: CreateEducationalModuleRequest): Promise<void> {
        return await this.post("educationalModules/create", {}, {
            ...createModuleRequest
        });
    }
}

export interface IEducationalModulesApi {
    getModulesByIds(getModulesRequest: GetModulesRequest): Promise<EducationalModule[]>;
    getAllModules(): Promise<EducationalModule[]>
    deleteModuleById(deleteModuleRequest: DeleteEducationalModuleRequest): Promise<void>
    createModule(createModuleRequest: CreateEducationalModuleRequest): Promise<void>
}
