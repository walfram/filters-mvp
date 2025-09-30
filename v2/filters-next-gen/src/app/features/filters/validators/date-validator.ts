import {AbstractControl, ValidationErrors, ValidatorFn} from '@angular/forms';

export function dateValidator(from: Date, to: Date): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    console.log('dateValidator, value', value);

    let dateToValidate: Date;

    if (value instanceof Date) {
      dateToValidate = value;
    } else if (typeof value === 'string' || typeof value === 'number') {
      dateToValidate = new Date(value);
      if (isNaN(dateToValidate.getTime())) {
        return { 'invalidDateType': true }; // Indicate that the value isn't a valid date format
      }
    } else if (value && typeof value.toDate === 'function') {
      dateToValidate = value.toDate();
    } else {
      return { 'unsupportedDateType': true };
    }

    if (dateToValidate < from || dateToValidate > to) {
      return { 'dateRangeInvalid': { from: from, to: to, actual: dateToValidate } };
    }

    return null;
  }
}
