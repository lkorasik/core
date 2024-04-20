import { ApiBase } from "../../ApiBase/ApiBase";
import { GroupDto } from "./GroupDto";
import { GetGroupDto } from "./GetGroupDto";
import { CreateGroupDto } from "./CreateGroupDto";
import { GetGroupIdDto } from "./GetGroupIdDto";
import { GenerateTokenDto } from "./GenerateTokenDto";

export class GroupsApi extends ApiBase implements IGroupsApi {
    public async getGroupsForProgram(programId: GetGroupDto): Promise<GroupDto[]> {
        return this.get("groups/group", {...programId});
    }

    public async createGroup(createGroup: CreateGroupDto): Promise<any> {
        return this.post("groups/group" , {}, {...createGroup});
    }

    public async getGroup(getGroup: GetGroupIdDto): Promise<GroupDto> {
        return this.get("groups/groupById", {...getGroup});
    }

    public async generateTokens(generateTokens: GenerateTokenDto): Promise<string[]> {
        return this.post("groups/token", {}, {...generateTokens});
    }
}

export interface IGroupsApi {
    getGroupsForProgram(programId: GetGroupDto): Promise<GroupDto[]>
    createGroup(createGroup: CreateGroupDto): Promise<any>
    getGroup(getGroup: GetGroupIdDto): Promise<GroupDto>
    generateTokens(generateTokens: GenerateTokenDto): Promise<string[]>
}