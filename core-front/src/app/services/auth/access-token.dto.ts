export interface AccessTokenDto {
    accessToken: string;
    userToken: string;
    accountEntityRole: UserRole;
}

export enum UserRole {
    Student = "Student",
    Admin = "Admin"
}
