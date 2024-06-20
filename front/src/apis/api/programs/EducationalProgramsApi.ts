import {ProgramInfoDto} from "./ProgramInfoDto";
import {ApiBase} from "../../ApiBase/ApiBase";
import { CreateProgramDto } from "./CreateProgramDto";
import { ProgramIdDto } from "./ProgramIdDto";
import { FullProgramDto } from "./FullProgramDto";
import { ShortProgramDTO } from "./ShortProgramDTO";
import { UpdateEducationalProgramDto } from "./UpdateEducationalProgramDto";
import { AvailableYearDto } from "./AvailableYearDto";
import { StudyPlanDto } from "./StudyPlanDto";
import { GetStudyPlanDto } from "./GetStudyPlanDto";
import { StudyPlanDto2 } from "./StudyPlanDto2";

export class ProgramsApi extends ApiBase implements IProgramsApi {
    public async getCurrentEducationalProgram(): Promise<ProgramInfoDto> {
        return this.get("programs/current");
    }

    public async createEducationalProgram(createEducationalProgram: CreateProgramDto): Promise<any> {
        return this.post("programs/create", {}, createEducationalProgram);
    }

    public async getEducationalProgramById(id: ProgramIdDto): Promise<FullProgramDto> {
        return this.get("programs/program", {...id});
    }

    public async getAllPrograms(): Promise<ShortProgramDTO[]> {
        return this.get("programs/all");
    }

    public async updateEducationalProgram(updateProgram: UpdateEducationalProgramDto): Promise<void> {
        return this.put("programs/program", {}, updateProgram);
    }

    public async getAvailableYears(programId: ProgramIdDto): Promise<AvailableYearDto[]> {
        return this.get("programs/availableYears", {...programId});
    }

    public async saveStudyPlan(studentSyllabus: StudyPlanDto): Promise<void> {
        return this.post("programs/plan", {}, studentSyllabus);
    }

    public async getStudyPlan(getStudyPlan: GetStudyPlanDto): Promise<StudyPlanDto2> {
        return this.post("programs/getPlan", {}, getStudyPlan)
    }
}

export interface IProgramsApi {
    getCurrentEducationalProgram(): Promise<ProgramInfoDto>
    createEducationalProgram(createEducationalProgram: CreateProgramDto): Promise<any>
    getEducationalProgramById(id: ProgramIdDto): Promise<FullProgramDto>
    getAllPrograms(): Promise<ShortProgramDTO[]>
    updateEducationalProgram(updateProgram: UpdateEducationalProgramDto): Promise<void>
    getAvailableYears(programId: ProgramIdDto): Promise<AvailableYearDto[]>
    saveStudyPlan(studentSyllabus: StudyPlanDto): Promise<void>
    getStudyPlan(getStudyPlan: GetStudyPlanDto): Promise<StudyPlanDto2>
}
