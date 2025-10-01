import {Filter} from '../../features/filters/types/filter';

export function filterToString(filter: Filter) {
  return `${filter.name} (criteria size=${filter.criteria.length})`;
}

