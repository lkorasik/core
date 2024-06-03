export interface AccessTokenDto {
    accessToken: string;
    userToken: string;
    userEntityRole: UserRole;
}

export enum UserRole {
    Student = "Student",
    Admin = "Admin"
}
