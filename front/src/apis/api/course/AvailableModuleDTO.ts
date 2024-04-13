import { AvailableCourseDTO } from "./AvailableCourseDTO";

export interface AvailableModuleDTO {
    id: string;
    name: string;
    courses: AvailableCourseDTO[]
}