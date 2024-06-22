import { throttleTime } from "rxjs";

export class SemesterPlan {
    id: string;
    semester: any;

    constructor(id: string, semester: any) {
        this.id = id;
        this.semester = semester;
    }
}