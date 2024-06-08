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
        return this.authorizedClient.get<GroupDto[]>("api/groups/groupsByProgram", params);

        // let headers = new HttpHeaders().append("Authorization", "Bearer " + sessionStorage.getItem("token"));
        // let params = new HttpParams().set("programId", group.programId);
        // return this.client.get<GroupDto[]>("api/groups/groupsByProgram", { headers, params });
    }

    public createGroup(createGroup: CreateGroupDTO) {
        return this.authorizedClient.post("api/groups/group", createGroup);

        // let headers = new HttpHeaders().append("Authorization", "Bearer " + sessionStorage.getItem("token"));
        // return this.client.post("api/groups/group", createGroup, { headers });
    }

    public getGroup(getGroup: GetGroupIdDto) {
        let params = new HttpParams().set("groupId", getGroup.groupId);
        return this.authorizedClient.get<GroupDto>("api/groups/groupById", params);

        // let headers = new HttpHeaders().append("Authorization", "Bearer " + sessionStorage.getItem("token"));
        // let params = new HttpParams().set("groupId", getGroup.groupId);
        // return this.client.get<GroupDto>("api/groups/groupById", { headers, params });
    }

    public getTokens(groupId: GetTokensDto) {
        let params = new HttpParams().set("groupId", groupId.groupId);
        return this.authorizedClient.get<TokenStatusDto[]>("api/groups/token", params);

        // let headers = new HttpHeaders().append("Authorization", "Bearer " + sessionStorage.getItem("token"));
        // let params = new HttpParams().set("groupId", groupId.groupId);
        // return this.client.get<TokenStatusDto[]>("api/groups/token", { headers, params });
    }

    public generateTokens(generateTokens: GenerateTokenDto) {
        let params = new HttpParams().set("groupId", generateTokens.groupId);
        return this.authorizedClient.post<string[]>("api/groups/token", generateTokens, params);

        // let headers = new HttpHeaders().append("Authorization", "Bearer " + sessionStorage.getItem("token"));
        // let params = new HttpParams().set("groupId", generateTokens.groupId);
        // return this.client.post<string[]>("api/groups/token", generateTokens, { headers, params });
    }

    public downloadTokensFile(groupNumber: string, groupId: GetTokensDto) {
        // let headers = new HttpHeaders()
            // .append("Authorization", "Bearer " + sessionStorage.getItem("token"))
            // .append("Content-Type", "application/octet-stream")
            // .append("Accept", "application/octet-stream");
        // let params = new HttpParams().set("groupId", groupId.groupId);
        // return this.client.get("api/groups/token_file", { params, headers })

        this.downloadFile("api/groups/token_file", groupNumber + ".txt", groupId.groupId);
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
