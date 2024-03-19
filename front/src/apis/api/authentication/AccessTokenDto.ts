import {UserRole} from "../../dto/UserRole";

export interface AccessTokenDto {
    accessToken: string;
    userToken: string;
    userRole: UserRole;
}
