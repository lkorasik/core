import { ModuleSelectionDto } from "./ModuleSelectionDto"

export interface StudyPlanDto {
    startYear: number
    modules: ModuleSelectionDto[]
}