export class TokenStatusDto {
    token: string;
    isActivated: boolean;

    constructor(token: string, isActivated: boolean) {
        this.token = token;
        this.isActivated = isActivated;
    }
}