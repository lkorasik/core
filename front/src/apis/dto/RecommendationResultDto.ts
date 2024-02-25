import {ModuleCoursesDto} from "./ModuleCoursesDto";
import {RecommendedCourse} from "./RecommendedCourse";

export interface RecommendationResultDto {
    perfectCourses: RecommendedCourse[];
    partiallySuitableCourses: RecommendedCourse[];
    complementaryCourses: RecommendedCourse[];
    moduleCourses: ModuleCoursesDto[];
}
