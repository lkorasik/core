export class GetModuleById {
    constructor(public id: string) { }
}

export class ModuleWithCoursesDTO {
    id: string
    name: string
    courses: CourseDTO[]
    
    constructor(id: string, name: string, courses: CourseDTO[]) {
        this.id = id;
        this.name = name;
        this.courses = courses;
    }
}

export class CourseDTO {
    id: string
    name: string
    
    constructor(id: string, name: string) {
        this.id = id;
        this.name = name;
    }
}

export class CreateModuleDto {
    moduleName: string;

    constructor(moduleName: string) {
        this.moduleName = moduleName;
    }
}
