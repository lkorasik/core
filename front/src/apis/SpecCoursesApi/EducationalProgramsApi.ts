import {EducationalProgramInfoDto} from "../dto/EducationalProgramInfoDto";
import {ApiBase} from "../ApiBase/ApiBase";
import { CreateEducationalProgram } from "../dto/CreateEducationalProgram";
import { EducationalProgramIdDto } from "../dto/EducationalProgramIdDto";
import { FullEducationalProgram } from "../dto/FullEducationalProgram";

export class EducationalProgramsApi extends ApiBase implements IEducationalProgramsApi {
    public async getEducationalProgramsList(): Promise<EducationalProgramInfoDto[]> {
        return this.get("educationalPrograms");
    }

    public async getCurrentEducationalProgram(): Promise<EducationalProgramInfoDto> {
        return this.get("educationalPrograms/current");
    }

    public async createEducationalProgramList(createEducationalProgram: CreateEducationalProgram): Promise<any> {
        return this.post("educationalPrograms/create", {}, createEducationalProgram);
    }

    public async getEducationalProgramById(id: EducationalProgramIdDto): Promise<FullEducationalProgram> {
        return this.post("educationalPrograms/program", {}, id);
    }
}

export interface IEducationalProgramsApi {
    getEducationalProgramsList(): Promise<EducationalProgramInfoDto[]>
    getCurrentEducationalProgram(): Promise<EducationalProgramInfoDto>
    createEducationalProgramList(createEducationalProgram: CreateEducationalProgram): Promise<any>
    getEducationalProgramById(id: EducationalProgramIdDto): Promise<FullEducationalProgram>
}
