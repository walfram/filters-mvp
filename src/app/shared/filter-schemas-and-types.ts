import {z} from 'zod';
import {v4} from 'uuid';

export const AmountConditionSchema = z.object({
  type: z.literal('amount'),
  operator: z.enum(['>', '>=', '=', '<=', '<']),
  value: z.number(),
});
export const amountOperators = AmountConditionSchema.shape.operator.options;

export const TitleConditionSchema = z.object({
  type: z.literal('title'),
  operator: z.enum(['startsWith', 'contains', 'equals']),
  value: z.string(),
});
export const titleOperators = TitleConditionSchema.shape.operator.options;

export const DateConditionSchema = z.object({
  type: z.literal('date'),
  operator: z.enum(['before', 'after', 'on']),
  value: z
    .string()
    .refine((val) => !isNaN(Date.parse(val)), {
      message: 'Invalid ISO date string',
    }),
});
export const dateOperators = DateConditionSchema.shape.operator.options;

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
