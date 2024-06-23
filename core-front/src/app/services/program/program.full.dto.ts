import { GroupDTO } from "./group.dto";

export class FullProgramDto {
    id: string;
    title: string;
    trainingDirection: string;
    groups: GroupDTO[];

    constructor(id: string, title: string, trainingDirection: string, groups: GroupDTO[]) {
        this.id = id;
        this.title = title;
        this.trainingDirection = trainingDirection;
        this.groups = groups;
    }
}