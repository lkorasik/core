export class SaveStudyPlanDTO {
    startYear: number
    modules: ModuleDTO[]

    constructor(startYear: number, modules: ModuleDTO[]) {
        this.startYear = startYear
        this.modules = modules
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
    semester: number

    constructor(courseId: string, semester: number) {
        this.courseId = courseId
        this.semester = semester
    }
}