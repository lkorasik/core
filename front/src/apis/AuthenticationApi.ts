import {ApiBase} from "./ApiBase/ApiBase";
import { RegistrationStudentDto } from "./dto/RegistrationDto";
import { RegistrationAdministrationDto } from "./dto/RegistrationAdministrationDto";
import {LoginDto} from "./dto/LoginDto";
import {TokenDto} from "./dto/TokenDto";
import {AccessTokenDto} from "./dto/AccessTokenDto";

export class AuthenticationApi extends ApiBase implements IAuthenticationApi {
    public async login(loginDto: LoginDto): Promise<AccessTokenDto> {
        return await this.post("authentication/login", {}, {
            ...loginDto
        })
    }

    public async registerStudent(registrationDto: RegistrationStudentDto): Promise<AccessTokenDto> {
        return await this.post("authentication/registerStudent", {}, {
            ...registrationDto
        })
    }

    public async registerAdmin(registrationDto: RegistrationAdministrationDto): Promise<AccessTokenDto> {
        return await this.post("authentication/registerAdministration", {}, {
            ...registrationDto
        })
    }

    public async validateToken(tokenDto: TokenDto): Promise<void> {
        return await this.post("authentication/validateToken", {}, {
            ...tokenDto
        })
    }

}

export interface IAuthenticationApi {
    registerStudent(registrationDto: RegistrationStudentDto): Promise<AccessTokenDto>;
    registerAdmin(registrationDto: RegistrationAdministrationDto): Promise<AccessTokenDto>;
    login(loginDto: LoginDto): Promise<AccessTokenDto>;
    validateToken(tokenDto: TokenDto): Promise<void>;
}
