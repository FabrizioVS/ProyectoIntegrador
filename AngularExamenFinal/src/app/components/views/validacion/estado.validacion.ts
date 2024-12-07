import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

// Definición del validador personalizado
export function validEstado(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const estado = control.value;
    // Cambia la lógica aquí según lo que necesites
    const esValido = estado === 'A' || estado === 'I'; // Por ejemplo, debe ser 'A' o 'I'

    return esValido ? null : { validEstado: true }; // Devuelve el error con el nombre 'validEstado'
  };
}
