export class CreateModuleDto {
    moduleName: string;

    constructor(moduleName: string) {
        this.moduleName = moduleName;
    }
}
