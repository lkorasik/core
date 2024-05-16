import {ApiBase} from "../../ApiBase/ApiBase";
import { RegistrationDto } from "./RegistrationDto";
import {LoginDto} from "./LoginDto";
import {TokenDto} from "./TokenDto";
import {AccessTokenDto} from "./AccessTokenDto";

export class AuthenticationApi extends ApiBase implements IAuthenticationApi {
    public async login(loginDto: LoginDto): Promise<AccessTokenDto> {
        return await this.post("authentication/login", {}, {
            ...loginDto
        })
    }

    public async register(registrationDto: RegistrationDto): Promise<AccessTokenDto> {
        return await this.post("authentication/register", {}, {
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
    login(loginDto: LoginDto): Promise<AccessTokenDto>;
    validateToken(tokenDto: TokenDto): Promise<void>;
    register(registrationDto: RegistrationDto): Promise<AccessTokenDto>;
}
