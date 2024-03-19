import {ModuleCoursesDto} from "./ModuleCoursesDto";
import {RecommendedCourseDto} from "./RecommendedCourseDto";

export interface RecommendationResultDto {
    perfectCourses: RecommendedCourseDto[];
    partiallySuitableCourses: RecommendedCourseDto[];
    complementaryCourses: RecommendedCourseDto[];
    moduleCourses: ModuleCoursesDto[];
}
