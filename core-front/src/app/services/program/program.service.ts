import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ProgramDTO } from "./program.dto";
import { ProgramIdDto } from "./program.id.dto";
import { FullProgramDto } from "./program.full.dto";
import { CreateProgramDTO } from "./createProgram.dto";
import { SaveStudyPlanDTO } from "./saveStudyPlan.dto";
import { AuthorizedHttpClient } from "../authorizedHttpClient";
import { Syllabus } from "./syllabus.dto";
import { GetNewPlan } from "./getNewPlan.dto";
import { ModuleResponse, ReqResponse } from "./getAllSyllabi2.dto";

@Injectable({
    providedIn: 'root'
})
export class ProgramService {
    constructor(private client: HttpClient, private authorizedClient: AuthorizedHttpClient) {}

    getAllPrograms(): Observable<ProgramDTO[]> {
        return this.client.get<ProgramDTO[]>("api/program/all");
    }

    getEducationalProgramById(id: ProgramIdDto) {
        let params = new HttpParams().set("id", id.id);
        return this.authorizedClient.get<FullProgramDto>("api/program", params);
    }

    createEducationalProgram(createEducationalProgram: CreateProgramDTO) {
        return this.authorizedClient.post("api/program", createEducationalProgram);
    }

    saveStudyPlan(saveStudyPlan: SaveStudyPlanDTO) {
        return this.authorizedClient.post("api/program/plan", saveStudyPlan);
    }

    saveStudyPlan2(id: string, startYear: number) {
        let params = new HttpParams().set("programId", id).set("startYear", startYear);
        return this.authorizedClient.get<ReqResponse[]>("api/syllabus/plan2", params);
    }

    getNewPlan(id: string, startYear: string) {
        let params = new HttpParams().set("programId", id).set("startYear", startYear);
        return this.authorizedClient.get<GetNewPlan>("api/program/getNewPlan", params);
    }
}