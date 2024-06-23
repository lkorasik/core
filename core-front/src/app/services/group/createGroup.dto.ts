export class CreateGroupDTO {
    number: string
    year: number
    programId: string

    constructor(number: string, year: number, programId: string) {
        this.number = number;
        this.year = year;
        this.programId = programId;
    }
}