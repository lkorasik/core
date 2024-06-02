import { Routes } from '@angular/router';
import { AdminMainScreenComponent } from './components/admin-main-screen/admin-main-screen.component';
import { AuthFormComponent } from './components/auth-form/auth-form.component';

export const routes: Routes = [
    { path: '', component: AuthFormComponent },
    { path: 'admin', component: AdminMainScreenComponent },
];