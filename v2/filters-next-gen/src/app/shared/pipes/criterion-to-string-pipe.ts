import { Pipe, PipeTransform } from '@angular/core';
import {Criterion} from '../../features/filters/types/criterion';

@Pipe({
  name: 'criterionToString'
})
export class CriterionToStringPipe implements PipeTransform {

  transform(value: Criterion): string {
    return criterionToString(value);
  }

}

const operatorMap = {
  number: {
    eq: 'equals',
    gt: 'greater than',
    gte: 'greater than or equal to',
    lt: 'less than',
    lte: 'less than or equal to',
  },
  date: {
    before: 'before',
    after: 'after',
    equals: 'equals',
  },
  string: {
    eq: 'equals',
    contains: 'contains',
    startsWith: 'starts with',
    endsWith: 'ends with',
  },
} as const;

function operatorToString(criterion: Criterion) {
  switch (criterion.type) {
    case 'number':
      return operatorMap.number[criterion.operator];
    case 'date':
      return operatorMap.date[criterion.operator];
    case 'string':
      return operatorMap.string[criterion.operator];
  }
}

export function dateToString(date: Date | string) {
  const dateObj = typeof date === 'string' ? new Date(date) : date;
  return dateObj.toLocaleDateString('et');
}

function criterionToString(criterion: Criterion) {
  switch (criterion.type) {
    case 'number':
      return `Amount ${operatorToString(criterion)} ${criterion.value}`;
    case 'string':
      return `Title ${operatorToString(criterion)} ${criterion.value}`;
    case 'date':
      return `Date ${operatorToString(criterion)} ${dateToString(criterion.value)}`;
    default:
      return `unknown criterion type ${JSON.stringify(criterion)}`;
  }
}
