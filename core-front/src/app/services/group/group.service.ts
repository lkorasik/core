import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GetGropupDTO } from './getGroup.dto';
import { GroupDto } from './group.dto';
import { CreateGroupDTO } from './createGroup.dto';
import { GetGroupIdDto } from './getGroupId.dto';
import { GetTokensDto } from './getTokens.dto';
import { TokenStatusDto } from './tokenStatus.dto';
import { GenerateTokenDto } from './getToken.dto';

@Injectable({
    providedIn: 'root'
})
export class GroupService {
    constructor(private client: HttpClient) { }

    public getGroupsForProgram(group: GetGropupDTO) {
        let headers = new HttpHeaders().append("Authorization", "Bearer " + sessionStorage.getItem("token"));
        let params = new HttpParams().set("programId", group.programId);
        return this.client.get<GroupDto[]>("api/groups/groupsByProgram", { headers, params });
    }

    public createGroup(createGroup: CreateGroupDTO) {
        let headers = new HttpHeaders().append("Authorization", "Bearer " + sessionStorage.getItem("token"));
        return this.client.post("api/groups/group", createGroup, { headers });
    }

    public getGroup(getGroup: GetGroupIdDto) {
        let headers = new HttpHeaders().append("Authorization", "Bearer " + sessionStorage.getItem("token"));
        let params = new HttpParams().set("groupId", getGroup.groupId);
        return this.client.get<GroupDto>("api/groups/groupById", { headers, params });
    }

    public getTokens(groupId: GetTokensDto) {
        let headers = new HttpHeaders().append("Authorization", "Bearer " + sessionStorage.getItem("token"));
        let params = new HttpParams().set("groupId", groupId.groupId);
        return this.client.get<TokenStatusDto[]>("api/groups/token", { headers, params });
    }

    public generateTokens(generateTokens: GenerateTokenDto) {
        let headers = new HttpHeaders().append("Authorization", "Bearer " + sessionStorage.getItem("token"));
        let params = new HttpParams().set("groupId", generateTokens.groupId);
        return this.client.post<string[]>("api/groups/token", generateTokens, { headers, params });
    }
}
