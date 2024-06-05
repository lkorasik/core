import { Component } from '@angular/core';
import { ToolbarComponent } from '../toolbar/toolbar.component';
import { SaveButtonComponent } from '../save-button/save-button.component';
import { CloseButtonComponent } from '../close-button/close-button.component';
import { TextFieldComponent } from '../text-field/text-field.component';
import { ProgramService } from '../../services/program/program.service';
import { CourseDto, FullModuleDto } from '../../services/program/fullModule.dto';

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
    }

    setName(v: string) {
        console.log("Set name")
    }

    setTrainingDirection(v: string) {
        console.log("ser train")
    }

    getClass(module: Module, course: CourseDto, semesterNumber: number) {
        const index = module.courses.findIndex(c => c.id == course.id)
        if (module.blockFlags[index][semesterNumber - 1] == 0) {
            return "enabled"
        } else {
            return "disabled"
        }
    }

    getMarker(module: Module, course: CourseDto, semesterNumber: number) {
        const index = module.courses.findIndex(c => c.id == course.id)
        if (module.selectionFlags[index][semesterNumber - 1] == 0) {
            return "X"
        } else {
            return ""
        }
    }

    onClick(module: Module, course: CourseDto, semesterNumber: number) {
        console.log("Click " + module.name + " " + course.name + " " + semesterNumber)

        const selectedCourse = this.getElement(module, course)!
        const index = module.courses.findIndex(c => c.id == course.id)

        if (module.selectionFlags[index][semesterNumber - 1] == 0) {
            module.selectionFlags[index][semesterNumber - 1] = 1;

            if (module.blockFlags[index][semesterNumber - 1] != 0) {
                return
            }
            for (let i = 0; i < module.courses.length; i++) {
                if (i != index) {
                    module.blockFlags[i][semesterNumber - 1]++
                }
            }
            for (let i = 0; i < 4; i++){
                if (i != semesterNumber - 1) {
                    module.blockFlags[index][i]++
                }
            }
        } else if (module.selectionFlags[index][semesterNumber - 1] == 1) {
            module.selectionFlags[index][semesterNumber - 1] = 0;

            if (module.blockFlags[index][semesterNumber - 1] != 0) {
                return
            }
            for (let i = 0; i < module.courses.length; i++) {
                if (i != index) {
                    module.blockFlags[i][semesterNumber - 1]--
                }
            }
            for (let i = 0; i < 4; i++){
                if (i != semesterNumber - 1) {
                    module.blockFlags[index][i]--
                }
            }
        }
    }

    private getElement(module: Module, course: CourseDto) {
        return this.modules2.find(m => m.id == module.id)?.courses.find(c => c.id == course.id)!
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

        for(let i = 0; i < courses.length; i++) {
            this.blockFlags[i] = [0, 0, 0, 0];
        }
        for(let i = 0; i < courses.length; i++) {
            this.selectionFlags[i] = [0, 0, 0, 0];
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