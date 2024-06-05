import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ProgramDTO } from "./program.dto";
import { ProgramIdDto } from "./program.id.dto";
import { FullProgramDto } from "./program.full.dto";
import { CreateProgramDTO } from "./createProgram.dto";
import { FullModuleDto } from "./fullModule.dto";

@Injectable({
    providedIn: 'root'
})
export class ProgramService {
    constructor(private client: HttpClient) {}

    getAllPrograms(): Observable<ProgramDTO[]> {
        return this.client.get<ProgramDTO[]>("api/programs/all");
    }

    getAllModules2() {
        let headers = new HttpHeaders().append("Authorization", "Bearer " + sessionStorage.getItem("token"));
        return this.client.get<FullModuleDto[]>("api/modules/all2", { headers });
    }

    getEducationalProgramById(id: ProgramIdDto) {
        let headers = new HttpHeaders().append("Authorization", "Bearer " + sessionStorage.getItem("token"));
        let params = new HttpParams().set("id", id.id);
        return this.client.get<FullProgramDto>("api/programs/program", { headers, params });
    }

    createEducationalProgram(createEducationalProgram: CreateProgramDTO) {
        let headers = new HttpHeaders().append("Authorization", "Bearer " + sessionStorage.getItem("token"));
        return this.client.post("api/programs/create", createEducationalProgram, { headers });
    }
}