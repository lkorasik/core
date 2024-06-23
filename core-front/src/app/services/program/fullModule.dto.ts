export class FullModuleDto {
    id: string;
    name: string;
    courses: CourseDto[];

    constructor(id: string, name: string, courses: CourseDto[]) {
        this.id = id;
        this.name = name;
        this.courses = courses;
    }
}

export class CourseDto {
    id: string;
    name: string;

    constructor(id: string, name: string) {
        this.id = id;
        this.name = name;
    }
}