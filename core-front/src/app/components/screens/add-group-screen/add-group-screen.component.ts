import { Component } from '@angular/core';
import {Location} from '@angular/common';
import { TitleComponent } from '../../base_components/title/title.component';
import { TextFieldComponent } from '../../base_components/text-field/text-field.component';
import { ToolbarComponent } from '../../base_components/toolbar/toolbar.component';
import { SaveButtonComponent } from '../../base_components/save-button/save-button.component';
import { CloseButtonComponent } from '../../base_components/close-button/close-button.component';
import { GroupService } from '../../../services/group/group.service';
import { CreateGroupDTO } from '../../../services/group/createGroup.dto';

@Component({
    selector: 'app-add-group-screen',
    standalone: true,
    imports: [TitleComponent, TextFieldComponent, ToolbarComponent, SaveButtonComponent, CloseButtonComponent],
    templateUrl: './add-group-screen.component.html',
    styleUrl: './add-group-screen.component.css'
})
export class AddGroupScreenComponent {
    id: string = sessionStorage.getItem('programId') || "";
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
