import { Routes } from '@angular/router';
import { ModuleScreenComponent } from './components/screens/module-screen/module-screen.component';
import { GroupScreenComponent } from './components/screens/group-screen/group-screen.component';
import { AddModuleScreenComponent } from './components/screens/add-module-screen/add-module-screen.component';
import { NotFoundScreenComponent } from './components/screens/not-found-screen/not-found-screen.component';
import { WelcomeScreenComponent } from './components/screens/welcome-screen/welcome-screen.component';
import { AdminMainScreenComponent } from './components/screens/admin-main-screen/admin-main-screen.component';
import { AddEducationalProgramScreenComponent } from './components/screens/add-educational-program-screen/add-educational-program-screen.component';
import { AddGroupScreenComponent } from './components/screens/add-group-screen/add-group-screen.component';
import { EditEducationalProgramScreenComponent } from './components/screens/edit-educational-program-screen/edit-educational-program-screen.component';
import { EducationalProgramGroupsScreenComponent } from './components/screens/educational-program-groups-screen/educational-program-groups-screen.component';
import { EducationalProgramScreenComponent } from './components/screens/educational-program-screen/educational-program-screen.component';

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
                path: 'module',
                component: ModuleScreenComponent
            },
            {
                path: 'module/add',
                component: AddModuleScreenComponent
            }
        ]
     },
     {
        path: '**',
        component: NotFoundScreenComponent
     }
];