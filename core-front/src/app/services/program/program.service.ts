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

@Injectable({
    providedIn: 'root'
})
export class ProgramService {
    constructor(private client: HttpClient, private authorizedClient: AuthorizedHttpClient) {}

    getAllPrograms(): Observable<ProgramDTO[]> {
        return this.client.get<ProgramDTO[]>("api/programs/all");
    }

    getAllModulesWithCourses(): Observable<FullModuleDto[]> {
        return this.authorizedClient.get<FullModuleDto[]>("api/modules/allWithCourses");
    }

    getEducationalProgramById(id: ProgramIdDto) {
        let params = new HttpParams().set("id", id.id);
        return this.authorizedClient.get<FullProgramDto>("api/programs/program", params);
    }

    createEducationalProgram(createEducationalProgram: CreateProgramDTO) {
        return this.authorizedClient.post("api/programs/create", createEducationalProgram);
    }

    saveStudyPlan(saveStudyPlan: SaveStudyPlanDTO) {
        return this.authorizedClient.post("api/programs/plan", saveStudyPlan);
    }
    
    getAllSyllabi(id: ProgramIdDto) {
        let params = new HttpParams().set("programId", id.id);
        return this.authorizedClient.post<Syllabus[]>("api/programs/getPlan", params);
    }
}