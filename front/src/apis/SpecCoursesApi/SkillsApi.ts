import {ApiBase} from "../ApiBase/ApiBase";
import {SkillInfoDto} from "../dto/SkillInfoDto";
import {SkillDto} from "../dto/SkillDto";
import {SaveSkillsRequest} from "../dto/SaveSkillsRequest";

export class SkillsApi extends ApiBase implements ISkillsApi {
    public async getAllSkills(): Promise<SkillInfoDto[]> {
        return await this.get("skills");
    }

    public async getActualSkills(): Promise<SkillDto[]> {
        return await this.get("skills/actual");
    }

    public async getDesiredSkills(): Promise<SkillDto[]> {
        return await this.get("skills/desired");
    }

    public async saveActualSkills(skillsRequest: SaveSkillsRequest): Promise<void> {
        return await this.post("skills/actual", {}, {
            ...skillsRequest
        });
    }

    public async saveDesiredSkills(skillsRequest: SaveSkillsRequest): Promise<void> {
        return await this.post("skills/desired", {}, {
            ...skillsRequest
        });
    }

}

export interface ISkillsApi {
    getAllSkills(): Promise<SkillInfoDto[]>
    getActualSkills(): Promise<SkillDto[]>
    saveActualSkills(skillsRequest: SaveSkillsRequest): Promise<void>
    getDesiredSkills(): Promise<SkillDto[]>
    saveDesiredSkills(skillsRequest: SaveSkillsRequest): Promise<void>
}
