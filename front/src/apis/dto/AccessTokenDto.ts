import {UserRole} from "./UserRole";

export interface AccessTokenDto {
    accessToken: string;
    login: string;
    userRole: UserRole;
}
