import { Component } from '@angular/core';
import { TitleComponent } from '../title/title.component';
import { AddButtonComponent } from '../add-button/add-button.component';
import { GridCard, GridComponent } from '../grid/grid.component';
import { ModuleService } from '../../services/module/module.service';
import { ModuleDTO } from '../../services/module/module.dto';
import { Router } from '@angular/router';

@Component({
    selector: 'app-modules-screen',
    standalone: true,
    imports: [TitleComponent, GridComponent, AddButtonComponent],
    templateUrl: './modules-screen.component.html',
    styleUrl: './modules-screen.component.css'
})
export class ModulesScreenComponent {
    modules: ModuleDTO[] = []

    constructor(private moduleService: ModuleService, private router: Router) {
        moduleService.getAllModules().subscribe(x => this.modules = x)
    }
    
    getGridCards() {
        return this.modules.map(x => new GridCard(x.name, "/administrator/module/" + x.id));
    }

    onAddButtonClick() {
        this.router.navigate(["administrator/module/add"])
    }
}
