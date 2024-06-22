import { Component } from '@angular/core';
import { TitleComponent } from '../../base_components/title/title.component';
import { ToolbarComponent } from '../../base_components/toolbar/toolbar.component';
import { TextFieldComponent } from '../../base_components/text-field/text-field.component';
import { FormsModule } from '@angular/forms';
import { SaveButtonComponent } from '../../base_components/save-button/save-button.component';
import { CloseButtonComponent } from '../../base_components/close-button/close-button.component';
import { CourceService } from '../../../services/course/cource.service';
import { CreateCourseDto } from '../../../services/course/dtos';
import { Callback } from '../../../services/callback';
import { Location } from '@angular/common';
import { NotificationService } from '../../../services/notification/notification.service';
import { DropdownComponent, DropdownItem } from '../../base_components/dropdown/dropdown.component';

@Component({
    selector: 'app-add-course-screen',
    standalone: true,
    imports: [
        TitleComponent, 
        ToolbarComponent, 
        TextFieldComponent, 
        FormsModule, 
        SaveButtonComponent, 
        CloseButtonComponent,
        DropdownComponent
    ],
    templateUrl: './add-course-screen.component.html',
    styleUrl: './add-course-screen.component.css'
})
export class AddCourseScreenComponent {
    moduleId: string = ""
    name: string = ""
    department: string = ""
    teacher: string = ""
    credits: number = 0
    contorlType: string = "Test"
    description: string = ""
    values: DropdownItem[] = [
        new DropdownItem("Зачет", "Test"),
        new DropdownItem("Экзамен", "Exam")
    ]

    constructor(
        private courseService: CourceService, 
        private location: Location, 
        private notificationService: NotificationService
    ) {
        this.moduleId = sessionStorage.getItem('moduleId') || ""
    }

    setName(name: string) {
        this.name = name
    }

    setDepartment(department: string) {
        this.department = department
    }

    setTeacher(teacher: string) {
        this.teacher = teacher
    }

    setCredits(credits: string) {
        this.credits = parseInt(credits)
    }

    setContorlType(contorlType: DropdownItem) {
        this.contorlType = contorlType.value
    }

    setDescription(description: string) {
        this.description = description
    }

    onSave() {
        let request = new CreateCourseDto(
            this.moduleId, 
            this.name, 
            this.credits, 
            this.contorlType, 
            this.department, 
            this.teacher, 
            this.description
        );
        let callback = new Callback(
            () => {
                console.log("Done")
                this.location.back()
                this.notificationService.info("Создание курса", "Курс создан")
            }, 
            (_: any) => this.notificationService.error("Создание курса", "Произошла ошибка"));
        this.courseService.createCourse(callback, request);
    }
}