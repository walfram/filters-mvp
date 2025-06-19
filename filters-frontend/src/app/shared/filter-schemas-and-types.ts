import {z} from 'zod';
import {v4} from 'uuid';

export const amountOperators = ['>', '>=', '=', '<=', '<'] as const;
export type AmountOperator = typeof amountOperators[number];
export const AmountConditionSchema = z.object({
  type: z.literal('amount'),
  operator: z.enum(amountOperators),
  value: z.number(),
});

export const titleOperators = ['startsWith', 'endsWith', 'contains', 'equals'] as const;
export type TitleOperator = typeof titleOperators[number];
export const TitleConditionSchema = z.object({
  type: z.literal('title'),
  operator: z.enum(titleOperators),
  value: z.string().min(1, 'Value is required'),
});

export const dateOperators = ['before', 'after', 'on'] as const;
export type DateOperator = typeof dateOperators[number];
export const DateConditionSchema = z.object({
  type: z.literal('date'),
  operator: z.enum(dateOperators),
  value: z
    .string()
    .refine((val) => !isNaN(Date.parse(val)), {
      message: 'Invalid ISO date string',
    }),
});

export type AmountCondition = z.infer<typeof AmountConditionSchema>;
export type TitleCondition = z.infer<typeof TitleConditionSchema>;
export type DateCondition = z.infer<typeof DateConditionSchema>;

export const FilterConditionSchema = z.union([
  AmountConditionSchema,
  TitleConditionSchema,
  DateConditionSchema,
]);

export type FilterCondition = z.infer<typeof FilterConditionSchema>;
export type FilterConditionType = FilterCondition['type'];

export const conditionTypes: FilterConditionType[] = FilterConditionSchema.options.map(
  schema => schema.shape.type.value
);

export const FilterSchema = z.object({
  id: z.string().uuid(),
  name: z.string().min(1, "Filter name is required"),
  conditions: z.array(FilterConditionSchema).min(1, "At least one condition is required"),
  active: z.boolean()
});

export const FiltersSchema = z.array(FilterSchema);

export type Filter = z.infer<typeof FilterSchema>;
export type Filters = z.infer<typeof FiltersSchema>;

export function newEmptyFilter(): Filter {
  return {
    id: v4(),
    name: '',
    conditions: [],
    active: false
  }
}

export function newEmptyCondition(type: FilterConditionType): FilterCondition {
  switch (type) {
    case 'amount':
      return {
        type: 'amount',
        operator: amountOperators[0],
        value: 0,
      };
    case 'title':
      return {
        type: 'title',
        operator: titleOperators[0],
        value: '',
      };
    case 'date':
      return {
        type: 'date',
        operator: dateOperators[0],
        value: new Date().toISOString().split('T')[0],
      };
    default:
      throw new Error(`Unsupported condition type: ${type}`);
  }
}
