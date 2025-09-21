import { Pipe, PipeTransform } from '@angular/core';
import {Criterion} from '../../features/filters/types/criterion';
import {criterionToString} from '../utils/formatters';

@Pipe({
  name: 'criterionToString'
})
export class CriterionToStringPipe implements PipeTransform {

  transform(value: Criterion): string {
    return criterionToString(value);
  }

}
