import { Component } from '@angular/core';
import { SaveButtonComponent } from '../../base_components/save-button/save-button.component';
import { EditButtonComponent } from '../../base_components/edit-button/edit-button.component';
import { TextFieldComponent } from '../../base_components/text-field/text-field.component';
import { CloseButtonComponent } from '../../base_components/close-button/close-button.component';
import { ActivatedRoute, Router } from '@angular/router';
import { GroupService } from '../../../services/group/group.service';
import { GetGroupIdDto } from '../../../services/group/getGroupId.dto';
import { TokenTableComponent } from '../../base_components/token-table/token-table.component';
import { GetTokensDto } from '../../../services/group/getTokens.dto';
import { AddButtonComponent } from '../../base_components/add-button/add-button.component';
import { GenerateTokenDto } from '../../../services/group/getToken.dto';
import { DownloadButtonComponent } from '../../base_components/download-button/download-button.component';
import { ToolbarComponent } from '../../base_components/toolbar/toolbar.component';
import { DialogComponent } from '../../base_components/dialog/dialog.component';

@Component({
    selector: 'app-group-screen',
    standalone: true,
    imports: [
        ToolbarComponent, 
        SaveButtonComponent, 
        EditButtonComponent, 
        TextFieldComponent, 
        CloseButtonComponent, 
        TokenTableComponent,
        AddButtonComponent,
        DialogComponent,
        DownloadButtonComponent
    ],
    templateUrl: './group-screen.component.html',
    styleUrl: './group-screen.component.css'
})
export class GroupScreenComponent {
    id: string = "";
    name: string = "";
    tokens: string[][] = []
    isOpen: boolean = false
    dialogValue: number = 0

    constructor(private route: ActivatedRoute, private groupService: GroupService) {
        console.log("Log")

        route.params.subscribe(x => this.id = x['id'])
        
        const request = new GetGroupIdDto(this.id);
        groupService.getGroup(request).subscribe(x => this.name = x.number);

        const request1 = new GetTokensDto(this.id);
        groupService.getTokens(request1).subscribe(x => this.tokens = x.map(y => [y.token, this.renderAccountStatus(y.isActivated)]));
    }

    private renderAccountStatus(isActivated: boolean) {
        if (isActivated) {
            return "Аккаунт зарегистрирован"
        } else {
            return "Аккаунт не зарегистрирован"
        }
    }

    onCloseDialogClick() {
        this.closeDialog()
    }

    onGenerateDialogClick() {
        const request = new GenerateTokenDto(this.dialogValue, this.id);
        const tokens = this.groupService.generateTokens(request).subscribe(x => x);
        this.closeDialog()
    }

    onAddButtonClick() {
        console.log("Click")
        this.isOpen = true
    }

    private closeDialog() {
        this.isOpen = false
    }

    updateTokensCount(count: string) {
        this.dialogValue = parseInt(count)
    }

    onDownloadClick() {
        const request = new GetTokensDto(this.id);
        this.groupService.downloadTokensFile(this.name, request);
    }
}
