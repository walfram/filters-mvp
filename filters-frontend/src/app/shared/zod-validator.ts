// zod-validator.ts
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { ZodSchema } from 'zod';

export function zodValidator<T>(schema: ZodSchema<T>): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const result = schema.safeParse(control.value);
    return result.success
      ? null
      : {
        zod: {
          message: result.error.errors.map((e) => e.message).join(', '),
          issues: result.error.errors,
        },
      };
  };
}
