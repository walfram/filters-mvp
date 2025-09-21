interface BaseCriterion {
  id: string;
}

export interface AmountCriterion extends BaseCriterion {
  type: 'number';
  operator: 'eq' | 'gt' | 'gte' | 'lt' | 'lte';
  value: number;
}

export interface TitleCriterion extends BaseCriterion {
  type: 'string';
  operator: 'eq' | 'contains' | 'startsWith' | 'endsWith';
  value: string;
  caseSensitive: boolean;
}

export interface DateCriterion extends BaseCriterion {
  type: 'date';
  operator: 'before' | 'after' | 'equals';
  value: Date;
}

export type Criterion = AmountCriterion | TitleCriterion | DateCriterion;
