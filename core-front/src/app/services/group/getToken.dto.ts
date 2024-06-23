export class GenerateTokenDto {
    count: number;
    groupId: string;

    constructor(count: number, groupId: string) {
        this.count = count;
        this.groupId = groupId;
    }
}