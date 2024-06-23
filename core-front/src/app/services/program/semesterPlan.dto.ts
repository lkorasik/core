import { throttleTime } from "rxjs";
import { SemesterDTO } from "./semester.dto";

export class SemesterPlan {
    id: string;
    semester: SemesterDTO;

    constructor(id: string, semester: any) {
        this.id = id;
        this.semester = semester;
    }
}