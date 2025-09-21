import {Filter} from '../../features/filters/types/filter';
import {Criterion} from '../../features/filters/types/criterion';

export function filterToString(filter: Filter) {
  return `${filter.name} (criteria size=${filter.criteria.length})`;
}

export function dateToString(date: Date) {
  return date.toLocaleDateString('et-EE');
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

export function operatorToString(criterion: Criterion) {
  switch (criterion.type) {
    case 'number':
      return operatorMap.number[criterion.operator];
    case 'date':
      return operatorMap.date[criterion.operator];
    case 'string':
      return operatorMap.string[criterion.operator];
  }
}

export function criterionToString(criterion: Criterion) {
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
