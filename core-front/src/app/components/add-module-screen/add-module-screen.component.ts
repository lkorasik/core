import { Component } from '@angular/core';
import { ToolbarComponent } from '../toolbar/toolbar.component';
import { SaveButtonComponent } from '../save-button/save-button.component';
import { AddButtonComponent } from '../add-button/add-button.component';
import { CloseButtonComponent } from '../close-button/close-button.component';
import { TextFieldComponent } from '../text-field/text-field.component';
import { ModuleService } from '../../services/module/module.service';
import {Location} from '@angular/common';
import { CreateModuleDto } from '../../services/module/createModule.dto';

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
