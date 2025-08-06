import { CommonModule } from '@angular/common';
import { Component, Output, EventEmitter } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'], 
})
export class LoginFormComponent {
  @Output() onSubmitLoginEvent = new EventEmitter();
  @Output() onSubmitRegisterEvent = new EventEmitter();

  //active: 'register' | 'login' = 'register';
  active: 'login' | 'register' = 'login';

  login: string = '';
  password: string = '';

  firstName: string = '';
  lastName: string = '';

  onLoginTab(): void {
    console.log('Switched to login');
    this.active = 'login';
    this.clearForm();
  }

  onRegisterTab(): void {
    console.log('Switched to register');
    this.active = 'register';
    this.clearForm();
  }

  onSubmitLogin(): void {
    this.onSubmitLoginEvent.emit({
      login: this.login,
      password: this.password,
    });
  }

  onSubmitRegister(): void {
    this.onSubmitRegisterEvent.emit({
      firstName: this.firstName,
      lastName: this.lastName,
      login: this.login,
      password: this.password,
    });
  }

  private clearForm(): void {
    this.login = '';
    this.password = '';
    this.firstName = '';
    this.lastName = '';
  }
}
