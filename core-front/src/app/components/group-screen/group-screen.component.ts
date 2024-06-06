import { Component } from '@angular/core';
import { ToolbarComponent } from '../toolbar/toolbar.component';
import { SaveButtonComponent } from '../save-button/save-button.component';
import { EditButtonComponent } from '../edit-button/edit-button.component';
import { TextFieldComponent } from '../text-field/text-field.component';
import { CloseButtonComponent } from '../close-button/close-button.component';
import { ActivatedRoute, Router } from '@angular/router';
import { GroupService } from '../../services/group/group.service';
import { GetGroupIdDto } from '../../services/group/getGroupId.dto';
import { TokenTableComponent } from '../token-table/token-table.component';
import { GetTokensDto } from '../../services/group/getTokens.dto';
import { TokenStatusDto } from '../../services/group/tokenStatus.dto';
import { AddButtonComponent } from '../add-button/add-button.component';
import { DialogComponent } from '../dialog/dialog.component';
import { GenerateTokenDto } from '../../services/group/getToken.dto';

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
        DialogComponent
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
}
