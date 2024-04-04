import {ProgramInfoDto} from "./ProgramInfoDto";
import {ApiBase} from "../../ApiBase/ApiBase";
import { CreateProgramDto } from "./CreateProgramDto";
import { ProgramIdDto } from "./ProgramIdDto";
import { FullProgramDto } from "./FullProgramDto";

export class ProgramsApi extends ApiBase implements IProgramsApi {
    public async getEducationalProgramsList(): Promise<ProgramInfoDto[]> {
        return this.get("programs");
    }

    public async getCurrentEducationalProgram(): Promise<ProgramInfoDto> {
        return this.get("programs/current");
    }

    public async createEducationalProgramList(createEducationalProgram: CreateProgramDto): Promise<any> {
        return this.post("programs/create", {}, createEducationalProgram);
    }

    public async getEducationalProgramById(id: ProgramIdDto): Promise<FullProgramDto> {
        return this.post("programs/program", {}, id);
    }
}

export interface IProgramsApi {
    getEducationalProgramsList(): Promise<ProgramInfoDto[]>
    getCurrentEducationalProgram(): Promise<ProgramInfoDto>
    createEducationalProgramList(createEducationalProgram: CreateProgramDto): Promise<any>
    getEducationalProgramById(id: ProgramIdDto): Promise<FullProgramDto>
}
