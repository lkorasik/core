import {ApiBase} from "../../ApiBase/ApiBase";
import {SkillInfoDto} from "./SkillInfoDto";
import {SaveSkillsDto} from "./SaveSkillsDto";
import { SkillDto } from "./SkillDto";

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

    public async saveActualSkills(skillsRequest: SaveSkillsDto): Promise<void> {
        return await this.post("skills/actual", {}, {
            ...skillsRequest
        });
    }

    public async saveDesiredSkills(skillsRequest: SaveSkillsDto): Promise<void> {
        return await this.post("skills/desired", {}, {
            ...skillsRequest
        });
    }

}

export interface ISkillsApi {
    getAllSkills(): Promise<SkillInfoDto[]>
    getActualSkills(): Promise<SkillDto[]>
    saveActualSkills(skillsRequest: SaveSkillsDto): Promise<void>
    getDesiredSkills(): Promise<SkillInfoDto[]>
    saveDesiredSkills(skillsRequest: SaveSkillsDto): Promise<void>
}
