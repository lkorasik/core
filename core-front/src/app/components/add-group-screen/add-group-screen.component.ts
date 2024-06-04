import { Component } from '@angular/core';
import { TitleComponent } from '../title/title.component';
import { TextFieldComponent } from '../text-field/text-field.component';
import { ToolbarComponent } from '../toolbar/toolbar.component';
import { SaveButtonComponent } from '../save-button/save-button.component';
import { CloseButtonComponent } from '../close-button/close-button.component';
import { ProgramService } from '../../services/program/program.service';
import {Location} from '@angular/common';
import { CreateGroupDTO } from '../../services/group/createGroup.dto';
import { GroupService } from '../../services/group/group.service';

@Component({
    selector: 'app-add-group-screen',
    standalone: true,
    imports: [TitleComponent, TextFieldComponent, ToolbarComponent, SaveButtonComponent, CloseButtonComponent],
    templateUrl: './add-group-screen.component.html',
    styleUrl: './add-group-screen.component.css'
})
export class AddGroupScreenComponent {
    id: string = localStorage.getItem('programId') || "";
    number: string = "";
    year: number = 0;

    constructor(private location: Location, private groupService: GroupService) { }

    setNumber(number: string) {
        this.number = number;
    }

    setYear(year: string) {
        this.year = parseInt(year);
    }

    onSave() {
        const request = new CreateGroupDTO(this.number, this.year, this.id);
        this.groupService.createGroup(request).subscribe(x => console.log(x));
        this.location.back()
    }
}
