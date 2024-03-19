import {RecommendedCourseDto} from "./RecommendedCourseDto";

export interface ModuleCoursesDto {
    moduleId: string;
    courses: RecommendedCourseDto[];
}
