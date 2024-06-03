import { Component } from '@angular/core';
import { ActionBarComponent } from '../action-bar/action-bar.component';
import { EducationalProgramScreenComponent } from '../educational-program-screen/educational-program-screen.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-admin-main-screen',
  standalone: true,
  imports: [ActionBarComponent, RouterOutlet],
  templateUrl: './admin-main-screen.component.html',
  styleUrl: './admin-main-screen.component.css'
})
export class AdminMainScreenComponent {
}
