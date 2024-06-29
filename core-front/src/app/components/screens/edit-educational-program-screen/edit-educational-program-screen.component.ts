import { Component } from '@angular/core';
import { ToolbarComponent } from '../../base_components/toolbar/toolbar.component';
import { SaveButtonComponent } from '../../base_components/save-button/save-button.component';
import { CloseButtonComponent } from '../../base_components/close-button/close-button.component';
import { TextFieldComponent } from '../../base_components/text-field/text-field.component';
import { ProgramService } from '../../../services/program/program.service';
import { DropdownComponent, DropdownItem } from '../../base_components/dropdown/dropdown.component';
import { DialogComponent } from '../../base_components/dialog/dialog.component';
import { ButtonComponent } from '../../base_components/button/button.component';
import { ModuleService } from '../../../services/module/module.service';
import { CourseSelectionDTO, ModuleDTO, SaveStudyPlanDTO } from '../../../services/program/saveStudyPlan.dto';

@Component({
    selector: 'app-edit-educational-program-screen',
    standalone: true,
    imports: [
        ToolbarComponent,
        SaveButtonComponent,
        CloseButtonComponent,
        TextFieldComponent,
        DropdownComponent,
        DialogComponent,
        ButtonComponent
    ],
    templateUrl: './edit-educational-program-screen.component.html',
    styleUrl: './edit-educational-program-screen.component.css'
})
export class EditEducationalProgramScreenComponent {
    id: string = ""
    title: string = ""
    trainingDirection: string = ""
    availableYears: DropdownItem[] = []
    years: Year[] = []
    modules: Module[] = []
    isOpen: boolean = false;
    selectedYear: DropdownItem | undefined = undefined

    constructor(private programService: ProgramService, private moduleService: ModuleService) {
        this.id = sessionStorage.getItem("programId")!;

        this.programService.getEducationalProgramById({ id: this.id }).subscribe(program => {
            this.title = program.title
            this.trainingDirection = program.trainingDirection
        })

        this.programService.getAllModulesWithCourses().subscribe(x => {
            this.modules = x.map(y => {
                const courses = y.courses.map(course => new Course(course.id, course.name));
                return new Module(y.id, y.name, courses)
            });
        })

        this.programService.getAllSyllabi({ id: this.id }).subscribe(x => {
            this.years = x.map(y => new Year(
                y.firstSemesterPlan.semester.id, 
                y.secondSemesterPlan.semester.id,
                y.thirdSemesterPlan.semester.id,
                y.fourthSemesterPlan.semester.id,
                y.firstSemesterPlan.semester.year
            ))
            this.availableYears = x.map(y => y.firstSemesterPlan.semester).map(y => new DropdownItem(y.year.toString(), y.id.toString()))
        })
    }

    onSave() {
        let year = this.years.filter(x => x.firstSemesterId == this.selectedYear?.value)[0]
        let modules = this.modules.filter(x => x.dialogSelected).map(x => new ModuleDTO(x.id, x.courses.map(y => {
            let semesterId:string = ""
            if (y.semesterNumber == 1) {
                semesterId = year.firstSemesterId
            } else if (y.semesterNumber == 2) {
                semesterId = year.secondSemesterId
            } else if (y.semesterNumber == 3) {
                semesterId = year.thirdSemesterId
            } else {
                semesterId = year.fourthSemesterId;
            }
            return new CourseSelectionDTO(y.id, semesterId)
        })))
        let request = new SaveStudyPlanDTO(this.id, this.selectedYear!.value, modules)
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

    onLeftButtonClick() {
        this.isOpen = false;
    }

    onRightButtonClick() {
        this.isOpen = false;
    }

    onSelectModules() {
        this.isOpen = true;
    }

    shouldShowPlanConstructor() {
        return this.selectedYear !== undefined
        // return this.modules.filter(x => x.dialogSelected).length != 0
    }

    onSelectModule(index: number) {
        this.modules[index].dialogSelected = !this.modules[index].dialogSelected;
    }

    onSelectYear(item: DropdownItem) {
        this.selectedYear = item

        this.programService.getNewPlan(this.id, this.selectedYear.label).subscribe(x => {
            // module
        })
    }
}

class Year {
    firstSemesterId: string
    secondSemesterId: string
    thirdSemesterId: string
    fourthSemesterId: string
    year: number

    constructor(firstSemesterId: string, secondSemesterId: string, thirdSemesterId: string, fourthSemesterId: string, year: number) {
        this.firstSemesterId = firstSemesterId
        this.secondSemesterId = secondSemesterId
        this.thirdSemesterId = thirdSemesterId
        this.fourthSemesterId = fourthSemesterId
        this.year = year
    }
}

class Module {
    id: string;
    name: string;
    courses: Course[];
    blockFlags: number[][] = [];
    selectionFlags: number[][] = [];
    dialogSelected: boolean;

    constructor(id: string, name: string, courses: Course[]) {
        this.id = id;
        this.name = name;
        this.courses = courses;
        this.dialogSelected = false;

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
                course.semesterNumber = semesterNumber;

                this.blockNearCells(index, semesterNumber);
            } else {
                // Ячейка выбрана
                this.selectionFlags[index][semesterNumber - 1] = 0;
                course.semesterNumber = undefined;

                this.releaseNearCells(index, semesterNumber);
            }
        }
    }

    private blockNearCells(index: number, semesterNumber: number) {
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
    }

    private releaseNearCells(index: number, semesterNumber: number) {
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

class Course {
    id: string;
    name: string;
    semesterNumber: number | undefined = undefined;

    constructor(id: string, name: string) {
        this.id = id;
        this.name = name;
    }
}