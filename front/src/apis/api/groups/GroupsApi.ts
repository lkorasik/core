import { ApiBase } from "../../ApiBase/ApiBase";
import { GroupDto } from "./GroupDto";
import { GetGroupDto } from "./GetGroupDto";

export class GroupsApi extends ApiBase implements IGroupsApi {
    public async getCurrentEducationalProgram(programId: GetGroupDto): Promise<GroupDto[]> {
        return this.get("groups/group", {...programId});
    }
}

export interface IGroupsApi {
    getCurrentEducationalProgram(programId: GetGroupDto): Promise<GroupDto[]>
}