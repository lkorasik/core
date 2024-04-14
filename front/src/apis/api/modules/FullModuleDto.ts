import { CourseDto } from "./CourseDto";

export interface FullModuleDto {
    id: string;
    name: string;
    courses: CourseDto[];
}