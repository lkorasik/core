import { Component } from '@angular/core';
import { SaveButtonComponent } from '../../base_components/save-button/save-button.component';
import { AddButtonComponent } from '../../base_components/add-button/add-button.component';
import { CloseButtonComponent } from '../../base_components/close-button/close-button.component';
import { TextFieldComponent } from '../../base_components/text-field/text-field.component';
import { ModuleService } from '../../../services/module/module.service';
import {Location} from '@angular/common';
import { ToolbarComponent } from '../../base_components/toolbar/toolbar.component';
import { CreateModuleDto } from '../../../services/module/dtos';

@Component({
    selector: 'app-add-module-screen',
    standalone: true,
    imports: [ToolbarComponent, SaveButtonComponent, AddButtonComponent, CloseButtonComponent, TextFieldComponent],
    templateUrl: './add-module-screen.component.html',
    styleUrl: './add-module-screen.component.css'
})
export class AddModuleScreenComponent {
    name: string = ""

    constructor(private moduleService: ModuleService, private location: Location) { }

    setName(name: string) {
        this.name = name
    }

    onSave() {
        const request = new CreateModuleDto(this.name)
        this.moduleService.createModule(request).subscribe(x => x);
        this.location.back()
    }
}
