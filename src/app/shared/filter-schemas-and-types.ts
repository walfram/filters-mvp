import { z } from 'zod';

const AmountConditionSchema = z.object({
  type: z.literal('amount'),
  operator: z.enum(['>', '>=', '=', '<=', '<']),
  value: z.number(),
});

const TitleConditionSchema = z.object({
  type: z.literal('title'),
  operator: z.enum(['startsWith', 'contains', 'equals']),
  value: z.string(),
});

const DateConditionSchema = z.object({
  type: z.literal('date'),
  operator: z.enum(['before', 'after', 'on']),
  value: z
    .string()
    .refine((val) => !isNaN(Date.parse(val)), {
      message: 'Invalid ISO date string',
    }),
});

const FilterConditionSchema = z.union([
  AmountConditionSchema,
  TitleConditionSchema,
  DateConditionSchema,
]);

export const FilterSchema = z.object({
  id: z.string().uuid(),
  name: z.string().min(1, "Filter name is required"),
  conditions: z.array(FilterConditionSchema).min(1, "At least one condition is required"),
  active: z.boolean()
});

export const FiltersSchema = z.array(FilterSchema);

export type Filter = z.infer<typeof FilterSchema>;
export type Filters = z.infer<typeof FiltersSchema>;
