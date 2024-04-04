import { CourseDto } from "./CourseDto"

export interface FullSemester {
    id: string
    requiredCourses: CourseDto[],
    specialCourses: CourseDto[],
    scienceWorks: CourseDto[]
}