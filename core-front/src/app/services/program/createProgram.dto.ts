export class CreateProgramDTO {
    name: string;
    trainingDirection: string;

    constructor(name: string, trainingDirection: string) {
        this.name = name;
        this.trainingDirection = trainingDirection;
    }
}
