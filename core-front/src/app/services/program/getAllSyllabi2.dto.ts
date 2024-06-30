export class ReqResponse {
    id: string
    year: number
    modules: ModuleResponse[]

    constructor(id: string, year: number, modules: ModuleResponse[]) {
        this.id = id
        this.year = year
        this.modules = modules
    }
}

export class ModuleResponse {
    id: string
    name: string
    courses: CourseResponse[]

    constructor(id: string, name: string, courses: CourseResponse[]) {
        this.id = id
        this.name = name
        this.courses = courses
    }
}

export class CourseResponse {
    id: string
    name: string
    semesterNumber: number

    constructor(id: string, name: string, semesterNumber: number) {
        this.id = id
        this.name = name
        this.semesterNumber = semesterNumber
    }
}