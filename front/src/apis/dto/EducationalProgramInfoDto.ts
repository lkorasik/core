export interface EducationalProgramInfoDto {
    id: string;
    name: string;
    semesterIdToRequiredCreditsCount: Record<string, number>;
}
