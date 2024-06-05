import { Component } from '@angular/core';
import { ToolbarComponent } from '../toolbar/toolbar.component';
import { SaveButtonComponent } from '../save-button/save-button.component';
import { CloseButtonComponent } from '../close-button/close-button.component';
import { TextFieldComponent } from '../text-field/text-field.component';
import { ProgramService } from '../../services/program/program.service';
import { CourseDto, FullModuleDto } from '../../services/program/fullModule.dto';
import { CourseSelectionDTO, ModuleDTO, SaveStudyPlanDTO } from '../../services/program/saveStudyPlan.dto';

@Component({
    selector: 'app-edit-educational-program-screen',
    standalone: true,
    imports: [ToolbarComponent, SaveButtonComponent, CloseButtonComponent, TextFieldComponent],
    templateUrl: './edit-educational-program-screen.component.html',
    styleUrl: './edit-educational-program-screen.component.css'
})
export class EditEducationalProgramScreenComponent {
    id: string = ""
    years: string[] = ["2023"]
    modules: FullModuleDto[] = []
    modules2: Module[] = []

    constructor(private programService: ProgramService) {
        programService.getAllModules2().subscribe(x => {
            this.modules = x
            this.modules2 = this.modules.map(module => {
                const courses = module.courses.map(course => new Course(course.id, course.name));
                return new Module(module.id, module.name, courses)
            })
        })
    }

    onSave() {
        console.log("Save")

        const modulesBody = this.modules2.map(x => new ModuleDTO(x.id, x.courses.map(y => new CourseSelectionDTO(y.id, y.semesterNumber!))))
        const request = new SaveStudyPlanDTO(parseInt(this.years[0]), modulesBody)
        this.programService.saveStudyPlan(request).subscribe(x => x)
    }

    setName(v: string) {
        console.log("Set name")
    }

    setTrainingDirection(v: string) {
        console.log("ser train")
    }

    getClass(module: Module, course: Course, semesterNumber: number) {
        const index = module.courses.findIndex(c => c.id == course.id);
        if (module.blockFlags[index][semesterNumber - 1] == 0) {
            return "enabled"
        } else {
            return "disabled"
        }
    }

    getMarker(module: Module, course: Course, semesterNumber: number) {
        const index = module.courses.findIndex(c => c.id == course.id)
        if (module.blockFlags[index][semesterNumber - 1] == 0) {
            if (module.selectionFlags[index][semesterNumber - 1] == 0) {
                return ""
            } else {
                return "X"
            }
        } else {
            return ""
        }
    }

    onClick(module: Module, course: Course, semesterNumber: number) {
        module.select(course, semesterNumber);
    }
}

class Module {
    id: string;
    name: string;
    courses: Course[];
    blockFlags: number[][] = [];
    selectionFlags: number[][] = [];

    constructor(id: string, name: string, courses: Course[]) {
        this.id = id;
        this.name = name;
        this.courses = courses;

        for (let i = 0; i < courses.length; i++) {
            this.blockFlags[i] = [0, 0, 0, 0];
        }
        for (let i = 0; i < courses.length; i++) {
            this.selectionFlags[i] = [0, 0, 0, 0];
        }
    }

    /**
     * Ячейка может быть заблокированной, ячейка может быть выбранной.
     * Заблокированная ячейка не может менять свое состояние. Выбранная ячейка может поменять свое состояние.
     * 
     * Если ячейка не заблокирована, то проверяем ее на выбор. 
     * Если ячейка не выбрана, то выбираем ее и блокируем крест.
     * Если ячейка выбрана, то снимаем выбор и разблокируем крест.
     * Если ячейка заблокирована, то ничего не делаем.
     */
    select(course: Course, semesterNumber: number) {
        const index = this.courses.findIndex(c => c.id == course.id)

        const blockFlag = this.blockFlags[index][semesterNumber - 1]
        if (blockFlag == 0) {
            // Ячейка не заблокирована
            const selectionFlag = this.selectionFlags[index][semesterNumber - 1]
            if (selectionFlag == 0) {
                // Ячейка не выбрана
                this.selectionFlags[index][semesterNumber - 1] = 1;
                course.semesterNumber = semesterNumber

                for (let i = 0; i < 4; i++) {
                    if (i != semesterNumber - 1) {
                        this.blockFlags[index][i]++
                    }
                }
                for (let i = 0; i < this.courses.length; i++) {
                    if (i != index) {
                        this.blockFlags[i][semesterNumber - 1]++
                    }
                }
            } else {
                // Ячейка выбрана
                this.selectionFlags[index][semesterNumber - 1] = 0;
                course.semesterNumber = undefined

                for (let i = 0; i < 4; i++) {
                    if (i != semesterNumber - 1) {
                        this.blockFlags[index][i]--
                    }
                }
                for (let i = 0; i < this.courses.length; i++) {
                    if (i != index) {
                        this.blockFlags[i][semesterNumber - 1]--
                    }
                }
            }
        }
    }
}

class Course {
    id: string;
    name: string;
    semesterNumber: number | undefined = undefined;

    constructor(id: string, name: string) {
        this.id = id;
        this.name = name;
    }
}