import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GetGropupDTO } from './getGroup.dto';
import { GroupDto } from './group.dto';
import { CreateGroupDTO } from './createGroup.dto';
import { GetGroupIdDto } from './getGroupId.dto';
import { GetTokensDto } from './getTokens.dto';
import { TokenStatusDto } from './tokenStatus.dto';
import { GenerateTokenDto } from './getToken.dto';
import { AuthorizedHttpClient } from '../authorizedHttpClient';

@Injectable({
    providedIn: 'root'
})
export class GroupService {
    constructor(private client: HttpClient, private authorizedClient: AuthorizedHttpClient) { }

    public getGroupsForProgram(group: GetGropupDTO) {
        let params = new HttpParams().set("programId", group.programId);
        return this.authorizedClient.get<GroupDto[]>("api/group/groupsByProgram", params);
    }

    public createGroup(createGroup: CreateGroupDTO) {
        return this.authorizedClient.post("api/group", createGroup);
    }

    public getGroup(getGroup: GetGroupIdDto) {
        let params = new HttpParams().set("groupId", getGroup.groupId);
        return this.authorizedClient.get<GroupDto>("api/group", params);
    }

    public getTokens(groupId: GetTokensDto) {
        let params = new HttpParams().set("groupId", groupId.groupId);
        return this.authorizedClient.get<TokenStatusDto[]>("api/group/tokens", params);
    }

    public generateTokens(generateTokens: GenerateTokenDto) {
        let params = new HttpParams().set("groupId", generateTokens.groupId);
        return this.authorizedClient.post<string[]>("api/group/tokens", generateTokens, params);
    }

    public downloadTokensFile(groupNumber: string, groupId: GetTokensDto) {
        this.downloadFile("api/group/tokenFile", groupNumber + ".txt", groupId.groupId);
    }

    downloadFile(url: string, fileName: string, groupId: string) {
        let headers = new HttpHeaders()
            .append("Authorization", "Bearer " + sessionStorage.getItem("token"))
            .append("Content-Type", "application/octet-stream")
            .append("Accept", "application/octet-stream");
        let params = new HttpParams().set("groupId", groupId);
        this.client.get(url, { params, headers, responseType: 'blob' }).subscribe((response: Blob) => {
            const downloadUrl = window.URL.createObjectURL(response);
            const link = document.createElement('a');
            link.href = downloadUrl;
            link.setAttribute('download', fileName);
            link.click();
            window.URL.revokeObjectURL(downloadUrl);
        });
    }
}
