import {ApiBase} from "../../ApiBase/ApiBase";

export class DocumentsApi extends ApiBase implements IDocumentsApi {
    public async generateDocument(): Promise<any> {
        // return await this.get("/document/generate");
        return await this.downloadFile("/document/generate", "plan.docx");
    }
}


export interface IDocumentsApi {
    generateDocument(): Promise<any>;
}
