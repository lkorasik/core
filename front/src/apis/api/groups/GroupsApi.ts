import { ApiBase } from "../../ApiBase/ApiBase";
import { GroupDto } from "./GroupDto";
import { GetGroupDto } from "./GetGroupDto";
import { CreateGroupDto } from "./CreateGroupDto";

export class GroupsApi extends ApiBase implements IGroupsApi {
    public async getGroupsForProgram(programId: GetGroupDto): Promise<GroupDto[]> {
        return this.get("groups/group", {...programId});
    }

    public async createGroup(createGroup: CreateGroupDto): Promise<any> {
        return this.post("groups/group" , {}, {...createGroup});
    }
}

export interface IGroupsApi {
    getGroupsForProgram(programId: GetGroupDto): Promise<GroupDto[]>
    createGroup(createGroup: CreateGroupDto): Promise<any>
}