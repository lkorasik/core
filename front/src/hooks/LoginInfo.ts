import {UserRole} from "../apis/dto/UserRole";

export interface LoginInfo {
    token: string;
    userRole: UserRole
}
