export class RegistrationDTO {
    constructor(public token: string, public password: string, public passwordAgain: string) { }
}