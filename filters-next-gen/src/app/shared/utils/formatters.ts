import {Filter} from '../../features/filters/types/filter';
import {Criterion} from '../../features/filters/types/criterion';

export function filterToString(filter: Filter) {
  return `${filter.name} (criteria size=${filter.criteria.length})`;
}

