export class SaveStudyPlanDTO {
    firstSemesterId: string
    modules: ModuleDTO[]

    constructor(firstSemesterId: string, modules: ModuleDTO[]) {
        this.firstSemesterId = firstSemesterId;
        this.modules = modules;
    }
}

export class ModuleDTO {
    moduleId: string
    courses: CourseSelectionDTO[]

    constructor(moduleId: string, courses: CourseSelectionDTO[]) {
        this.moduleId = moduleId
        this.courses = courses
    }
}

export class CourseSelectionDTO {
    courseId: string
    semesterId: string

    constructor(courseId: string, semesterId: string) {
        this.courseId = courseId
        this.semesterId = semesterId
    }
}
