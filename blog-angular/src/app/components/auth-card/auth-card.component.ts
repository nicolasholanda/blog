import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup, FormGroupDirective, NgForm,
  ValidationErrors,
  ValidatorFn,
  Validators
} from "@angular/forms";
import {ErrorStateMatcher} from "@angular/material/core";
import {UserService} from "../../services/user.service";
import {MessageService} from "../../services/message.service";
import {AuthService} from "../../services/auth.service";
import {NewUser} from "../../model/NewUser";
import {MatBottomSheetRef} from "@angular/material/bottom-sheet";
import {Credentials} from "../../model/Credentials";

@Component({
  selector: 'app-auth-card',
  templateUrl: './auth-card.component.html',
  styleUrls: ['./auth-card.component.css']
})
export class AuthCardComponent implements OnInit {

  state = AuthState.LOGIN;
  loginForm: FormGroup;
  registerForm: FormGroup;
  matcher = new MyErrorStateMatcher();

  constructor(private fb: FormBuilder,
              private userService: UserService,
              private authService: AuthService,
              private messageService: MessageService,
              private sheetRef: MatBottomSheetRef) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });

    this.registerForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      password: ['', Validators.required],
      confirmPassword: ['']
    }, { validator: this.checkPasswords });
  }

  ngOnInit(): void {
  }

  confirmCreateAccount() {
    let newUser: NewUser = {
      firstName: this.registerForm.get('firstName')?.value,
      lastName: this.registerForm.get('lastName')?.value,
      email: this.registerForm.get('email')?.value,
      password: this.registerForm.get('password')?.value
    }
    this.userService.create(newUser).subscribe(() =>
        this.authService.authenticate({email: newUser.email, password: newUser.password})
          .subscribe(() => {
            this.sheetRef.dismiss(true);
            this.messageService.successMessage('Usuário criado e autenticado.');
          }),
        error => this.messageService.catchError(error.error)
    );
  }

  confirmLogin() {
    let creds: Credentials = {
      email: this.loginForm.get('email')?.value,
      password: this.loginForm.get('password')?.value
    }
    this.authService.authenticate(creds).subscribe(() => {
      this.sheetRef.dismiss(true);
    }, error => this.messageService.catchError(error.error))
  }

  onCreateAccountClick() {
    this.state = AuthState.REGISTER;
  }

  onLoginClick() {
    this.state = AuthState.LOGIN;
  }

  isLogin() {
    return this.state === AuthState.LOGIN;
  }

  isRegister() {
    return this.state === AuthState.REGISTER;
  }

  checkPasswords: ValidatorFn = (group: AbstractControl):  ValidationErrors | null => {
    let pass = group.get('password')?.value;
    let confirmPass = group.get('confirmPassword')?.value
    return pass === confirmPass ? null : { notSame: true }
  }

}

export enum AuthState {
  LOGIN = "Login", REGISTER = "Criar conta"
}

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    return control?.parent?.errors && control.parent.errors['notSame']
  }
}
