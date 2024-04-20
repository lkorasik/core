import { ApiBase } from "../../ApiBase/ApiBase";
import { GroupDto } from "./GroupDto";
import { GetGroupDto } from "./GetGroupDto";
import { CreateGroupDto } from "./CreateGroupDto";
import { GetGroupIdDto } from "./GetGroupIdDto";
import { GenerateTokenDto } from "./GenerateTokenDto";
import { GetTokensDto } from "./GetTokensDto";

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

    public async getTokens(groupId: GetTokensDto): Promise<string[]> {
        return this.get("groups/token", {...groupId}, {});
    }
    
    public async downloadTokensFile(groupNumber: string, groupId: GetTokensDto): Promise<any> {
        return await this.downloadFile("groups/token_file", groupNumber + ".txt", {...groupId});
    }
}

export interface IGroupsApi {
    getGroupsForProgram(programId: GetGroupDto): Promise<GroupDto[]>
    createGroup(createGroup: CreateGroupDto): Promise<any>
    getGroup(getGroup: GetGroupIdDto): Promise<GroupDto>
    generateTokens(generateTokens: GenerateTokenDto): Promise<string[]>
    getTokens(groupId: GetTokensDto): Promise<string[]>
    downloadTokensFile(groupNumber: string, groupId: GetTokensDto): Promise<any>
}