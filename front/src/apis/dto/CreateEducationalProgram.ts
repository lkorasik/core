import { CreateSemester } from "./CreateSemester"

export interface CreateEducationalProgram {
    title: string
    recomendedCredits: number[]
    semesters: CreateSemester[]
}
