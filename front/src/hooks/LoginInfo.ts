import {UserRole} from "../apis/api/UserRole";

export interface LoginInfo {
    token: string;
    userEntityRole: UserRole
}
