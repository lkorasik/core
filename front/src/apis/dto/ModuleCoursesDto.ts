import {RecommendedCourse} from "./RecommendedCourse";

export interface ModuleCoursesDto {
    moduleId: string;
    courses: RecommendedCourse[];
}
