import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ProgramDTO } from "./program.dto";
import { ProgramIdDto } from "./program.id.dto";
import { FullProgramDto } from "./program.full.dto";
import { CreateProgramDTO } from "./createProgram.dto";
import { FullModuleDto } from "./fullModule.dto";
import { SaveStudyPlanDTO } from "./saveStudyPlan.dto";
import { AuthorizedHttpClient } from "../authorizedHttpClient";
import { GetAllSyllabi } from "./getAllSyllabi.dto";
import { Syllabus } from "./syllabus.dto";
import { GetNewPlan } from "./getNewPlan.dto";

@Injectable({
    providedIn: 'root'
})
export class ProgramService {
    constructor(private client: HttpClient, private authorizedClient: AuthorizedHttpClient) {}

    getAllPrograms(): Observable<ProgramDTO[]> {
        return this.client.get<ProgramDTO[]>("api/program/all");
    }

    getAllModulesWithCourses(): Observable<FullModuleDto[]> {
        return this.authorizedClient.get<FullModuleDto[]>("api/modules/allWithCourses");
    }

    getEducationalProgramById(id: ProgramIdDto) {
        let params = new HttpParams().set("id", id.id);
        return this.authorizedClient.get<FullProgramDto>("api/program/program", params);
    }

    createEducationalProgram(createEducationalProgram: CreateProgramDTO) {
        return this.authorizedClient.post("api/program/create", createEducationalProgram);
    }

    saveStudyPlan(saveStudyPlan: SaveStudyPlanDTO) {
        return this.authorizedClient.post("api/program/plan", saveStudyPlan);
    }
    
    getAllSyllabi(id: ProgramIdDto) {
        let params = new HttpParams().set("programId", id.id);
        return this.authorizedClient.post<Syllabus[]>("api/program/getPlan", params);
    }

    getNewPlan(id: string, startYear: string) {
        let params = new HttpParams().set("programId", id).set("startYear", startYear);
        return this.authorizedClient.get<GetNewPlan>("api/program/getNewPlan", params);
    }
}