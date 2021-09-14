import Swal from 'sweetalert2';
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor() {
  }

  successMessage(message: string): void {
    Swal.fire({
      title: 'Sucesso!',
      text: message,
      icon: 'success'
    });
  }

  errorMessage(message: string): void {
    Swal.fire({
      title: 'Erro',
      text: message,
      icon: 'error'
    });
  }

  catchError = (error) => {
    if(typeof error === "string") {
      error = JSON.parse(error)
    }
    let msg = error.errors ? error.errors.map(e => e.message).join('; ') : error.message ? error.message : 'Ocorreu um erro desconhecido.'
    this.errorMessage(msg);
  }
}
