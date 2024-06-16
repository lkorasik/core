import { GroupDTO } from "./group.dto";

export class FullProgramDto {
    id: string;
    title: string;
    groups: GroupDTO[];

    constructor(id: string, title: string, groups: GroupDTO[]) {
        this.id = id;
        this.title = title;
        this.groups = groups;
    }
}