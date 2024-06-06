import { Component } from '@angular/core';
import { ToolbarComponent } from '../toolbar/toolbar.component';
import { SaveButtonComponent } from '../save-button/save-button.component';
import { EditButtonComponent } from '../edit-button/edit-button.component';
import { TextFieldComponent } from '../text-field/text-field.component';
import { CloseButtonComponent } from '../close-button/close-button.component';
import { ActivatedRoute } from '@angular/router';
import { GroupService } from '../../services/group/group.service';
import { GetGroupIdDto } from '../../services/group/getGroupId.dto';

@Component({
    selector: 'app-group-screen',
    standalone: true,
    imports: [ToolbarComponent, SaveButtonComponent, EditButtonComponent, TextFieldComponent, CloseButtonComponent],
    templateUrl: './group-screen.component.html',
    styleUrl: './group-screen.component.css'
})
export class GroupScreenComponent {
    id: string = "";
    name: string = "";

    constructor(private route: ActivatedRoute, private groupService: GroupService) {
        route.params.subscribe(x => this.id = x['id'])
        
        const request = new GetGroupIdDto(this.id);
        groupService.getGroup(request).subscribe(x => this.name = x.number);
    }

    onSave() {

    }

    setNumber(number: string) {

    }

    setYear(year: string) {

    }
}
