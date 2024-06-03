import { Routes } from '@angular/router';
import { AdminMainScreenComponent } from './components/admin-main-screen/admin-main-screen.component';
import { AuthFormComponent } from './components/auth-form/auth-form.component';
import { EducationalProgramScreenComponent } from './components/educational-program-screen/educational-program-screen.component';
import { ModulesScreenComponent } from './components/modules-screen/modules-screen.component';

export const routes: Routes = [
    { 
        path: '', 
        component: AuthFormComponent 
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
                path: 'link2',
                component: ModulesScreenComponent
            }
        ]
     },
];