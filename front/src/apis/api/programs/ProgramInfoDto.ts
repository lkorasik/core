import { SelectedCourseDTO } from "./SelectedCourseDTO";

export interface ProgramInfoDto {
    id: string;
    name: string;
    recommendedCredits: number[];
    selectedCourses: SelectedCourseDTO[];
}
