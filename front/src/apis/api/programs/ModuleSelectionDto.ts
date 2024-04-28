import { CourseSelectionDto } from "./CourseSelectionDto";

export interface ModuleSelectionDto {
    moduleId: string,
    courses: CourseSelectionDto[]
}