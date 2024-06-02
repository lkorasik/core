import { Component } from '@angular/core';
import { ActionBarComponent } from '../action-bar/action-bar.component';

@Component({
  selector: 'app-admin-main-screen',
  standalone: true,
  imports: [ActionBarComponent],
  templateUrl: './admin-main-screen.component.html',
  styleUrl: './admin-main-screen.component.css'
})
export class AdminMainScreenComponent {
}
