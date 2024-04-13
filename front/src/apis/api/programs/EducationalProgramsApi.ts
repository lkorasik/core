import {ProgramInfoDto} from "./ProgramInfoDto";
import {ApiBase} from "../../ApiBase/ApiBase";
import { CreateProgramDto } from "./CreateProgramDto";
import { ProgramIdDto } from "./ProgramIdDto";
import { FullProgramDto } from "./FullProgramDto";
import { ShortProgramDTO } from "./ShortProgramDTO";

export class ProgramsApi extends ApiBase implements IProgramsApi {
    public async getCurrentEducationalProgram(): Promise<ProgramInfoDto> {
        return this.get("programs/current");
    }

    public async createEducationalProgramList(createEducationalProgram: CreateProgramDto): Promise<any> {
        return this.post("programs/create", {}, createEducationalProgram);
    }

    public async getEducationalProgramById(id: ProgramIdDto): Promise<FullProgramDto> {
        return this.post("programs/program", {}, id);
    }

    public async getAllPrograms(): Promise<ShortProgramDTO[]> {
        return this.get("programs/all");
    }
}

export interface IProgramsApi {
    getCurrentEducationalProgram(): Promise<ProgramInfoDto>
    createEducationalProgramList(createEducationalProgram: CreateProgramDto): Promise<any>
    getEducationalProgramById(id: ProgramIdDto): Promise<FullProgramDto>
    getAllPrograms(): Promise<ShortProgramDTO[]>
}
