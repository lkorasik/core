export class SaveStudyPlanDTO {
    startYear: number
    programId: string
    modules: ModuleDTO[]

    constructor(startYear: number, programId: string, modules: ModuleDTO[]) {
        this.startYear = startYear
        this.programId = programId
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
    semesterEntity: number

    constructor(courseId: string, semesterEntity: number) {
        this.courseId = courseId
        this.semesterEntity = semesterEntity
    }
}
