import { Component } from '@angular/core';
import { TitleComponent } from '../../base_components/title/title.component';
import { ActivatedRoute, Router } from '@angular/router';
import { ModuleService } from '../../../services/module/module.service';
import { CourseDTO, GetModuleById, ModuleWithCoursesDTO } from '../../../services/module/dtos';
import { GridCard, GridComponent } from '../../base_components/grid/grid.component';
import { AddButtonComponent } from '../../base_components/add-button/add-button.component';

@Component({
    selector: 'app-module-detail-screen',
    standalone: true,
    imports: [TitleComponent, GridComponent, AddButtonComponent],
    templateUrl: './module-detail-screen.component.html',
    styleUrl: './module-detail-screen.component.css'
})
export class ModuleDetailScreenComponent {
    id: string = "";
    name: string = "";
    courses: CourseDTO[] = [];
    
    constructor(private route: ActivatedRoute, private router: Router, private moduleService: ModuleService) {
        route.params.subscribe(x => this.id = x['id'])

        moduleService.getModuleById(new GetModuleById(this.id)).subscribe(x => this.onReceive(x))
    }

    onReceive(moduleWithCourses: ModuleWithCoursesDTO) {
        this.name = moduleWithCourses.name
        this.courses = moduleWithCourses.courses
    }

    getGridCards() {
        return this.courses.map(course => new GridCard(course.name, "/administrator/course/" + course.id));
    }

    onAddButtonClick() {
        sessionStorage.setItem('moduleId', this.id);
        this.router.navigate(["administrator/module/course/add"])
    }
}
