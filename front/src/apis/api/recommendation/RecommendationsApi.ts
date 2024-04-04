import {ApiBase} from "../../ApiBase/ApiBase";
import {RecommendationResultDto} from "./RecommendationResultDto";

export class RecommendationsApi extends ApiBase implements IRecommendationsApi {
    public async calculateRecommendations(): Promise<RecommendationResultDto> {
        return await this.get("recommendations");
    }
}

export interface IRecommendationsApi {
    calculateRecommendations(): Promise<RecommendationResultDto>;
}
