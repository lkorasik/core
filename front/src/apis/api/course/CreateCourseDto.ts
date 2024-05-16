export interface CreateCourseDto {
    moduleId: string;
    name: string;
    credits: number;
    controlType: string;
    department: string;
    teacher: string;
    description: string;
}
