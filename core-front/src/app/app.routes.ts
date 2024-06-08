import { Routes } from '@angular/router';
import { AdminMainScreenComponent } from './components/admin-main-screen/admin-main-screen.component';
import { AuthFormComponent } from './components/auth-form/auth-form.component';
import { EducationalProgramScreenComponent } from './components/educational-program-screen/educational-program-screen.component';
import { ModulesScreenComponent } from './components/modules-screen/modules-screen.component';
import { EducationalProgramGroupsScreenComponent } from './components/educational-program-groups-screen/educational-program-groups-screen.component';
import { AddEducationalProgramScreenComponent } from './components/add-educational-program-screen/add-educational-program-screen.component';
import { AddGroupScreenComponent } from './components/add-group-screen/add-group-screen.component';
import { EditEducationalProgramScreenComponent } from './components/edit-educational-program-screen/edit-educational-program-screen.component';
import { GroupScreenComponent } from './components/group-screen/group-screen.component';
import { WelcomeScreenComponent } from './components/welcome-screen/welcome-screen.component';

export const routes: Routes = [
    { 
        path: '', 
        component: WelcomeScreenComponent 
    },
    { 
        path: 'administrator', 
        component: AdminMainScreenComponent,
        children: [
            {
                path: 'educational_program',
                component: EducationalProgramScreenComponent
            },
            {
                path: 'educational_program/add',
                component: AddEducationalProgramScreenComponent
            },
            {
                path: 'educational_program/:id',
                component: EducationalProgramGroupsScreenComponent
            },
            {
                path: 'educational_program/edit/:id',
                component: EditEducationalProgramScreenComponent
            },
            {
                path: 'group/add',
                component: AddGroupScreenComponent
            },
            {
                path: 'group/:id',
                component: GroupScreenComponent
            },
            {
                path: 'link2',
                component: ModulesScreenComponent
            }
        ]
     },
];