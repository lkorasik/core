import {UserRole} from "../UserRole";

export interface AccessTokenDto {
    accessToken: string;
    userToken: string;
    userRole: UserRole;
}
