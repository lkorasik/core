import {ApiBase} from "../ApiBase/ApiBase";
import {Semester} from "../dto/Semester";

export class SemestersApi extends ApiBase implements ISemestersApi {
    public async getActualSemesters(): Promise<Semester[]> {
        return this.get("semesters/actual");
    }
}

export interface ISemestersApi {
    getActualSemesters(): Promise<Semester[]>
}
