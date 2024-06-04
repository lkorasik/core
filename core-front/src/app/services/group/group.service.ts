import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GetGropupDTO } from './getGroup.dto';
import { GroupDto } from './group.dto';

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
}
