import {ApiBase} from "../../ApiBase/ApiBase";
import { SemesterDto } from "./SemesterDto";

export class SemestersApi extends ApiBase implements ISemestersApi {
    public async getActualSemesters(): Promise<SemesterDto[]> {
        return this.get("semesters/actual");
    }
}

export interface ISemestersApi {
    getActualSemesters(): Promise<SemesterDto[]>
}
