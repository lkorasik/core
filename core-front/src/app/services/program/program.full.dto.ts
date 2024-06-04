export class FullProgramDto {
    id: string;
    title: string;
    trainingDirection: string;

    constructor(id: string, title: string, trainingDirection: string) {
        this.id = id;
        this.title = title;
        this.trainingDirection = trainingDirection;
    }
}