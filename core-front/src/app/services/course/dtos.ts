export class CreateCourseDto {
    moduleId: string;
    name: string;
    credits: number;
    controlType: string;
    department: string;
    teacher: string;
    description: string;

    constructor(
        moduleId: string,
        name: string,
        credits: number,
        controlType: string,
        department: string,
        teacher: string,
        description: string
    ) {
        this.moduleId = moduleId;
        this.name = name;
        this.credits = credits;
        this.controlType = controlType;
        this.department = department;
        this.teacher = teacher;
        this.description = description;
    }
}
