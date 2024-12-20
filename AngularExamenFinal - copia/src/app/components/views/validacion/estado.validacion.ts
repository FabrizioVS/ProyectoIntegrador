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
export function porLoMenosUnaMayuscula(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value || '';
    const tieneMayuscula = /[A-Z]/.test(value);
    return tieneMayuscula ? null : { PorLoMenosUnaMayuscula: true };
  };
}

// Validador para al menos un número
export function porLoMenosUnNumero(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value || '';
    const tieneNumero = /\d/.test(value);
    return tieneNumero ? null : { PorLoMenosUnNumero: true };
  };
}
// Validador para al menos un carácter especial
export function porLoMenosUnCaracteEspecial(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value || '';
    const tieneCaracterEspecial = /[!@#$%^&*(),.?":{}|<>]/.test(value);
    return tieneCaracterEspecial ? null : { validSpeciPorLoMenosUnCaracteEspecialalChar: true };
  };
}
