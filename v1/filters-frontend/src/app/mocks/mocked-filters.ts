import {Filters} from '../shared/filter-schemas-and-types';

export const MOCKED_FILTERS: Filters = [
  {
    id: 'a3f9e730-8bde-4c72-b3fa-bb2f3aab0c2d',
    name: 'High Value Transactions',
    conditions: [
      {type: 'amount', operator: '>', value: 1000}
    ],
    active: true
  },
  {
    id: 'b1d68c12-4ebd-4c9e-9f6c-df62c3693db1',
    name: 'Recent Large Purchases',
    conditions: [
      {type: 'date', operator: 'after', value: '2025-01-01'},
      {type: 'amount', operator: '>=', value: 500}
    ],
    active: true
  },
  {
    id: 'f4c27a5e-00c4-4ac1-9f60-376e09f56f59',
    name: 'Premium Title Filter',
    conditions: [
      {type: 'title', operator: 'startsWith', value: 'A'},
      {type: 'amount', operator: '>', value: 200},
      {type: 'date', operator: 'after', value: '2024-01-01'}
    ],
    active: false
  },
  {
    id: '65c858e3-d08f-4b9e-96d2-4f98b23d2130',
    name: 'Complex Multi-Condition Filter',
    conditions: [
      {type: 'amount', operator: '=', value: 500},
      {type: 'title', operator: 'contains', value: 'Premium'},
      {type: 'date', operator: 'on', value: '2024-12-25'},
      {type: 'amount', operator: '<', value: 1000}
    ],
    active: false
  },
  {
    id: '8e79285b-f30e-4e1e-a269-f4c55e34f21b',
    name: 'Comprehensive Legacy Filter',
    conditions: [
      {type: 'date', operator: 'before', value: '2023-01-01'},
      {type: 'title', operator: 'equals', value: 'Legacy Item'},
      {type: 'amount', operator: '<=', value: 100},
      {type: 'title', operator: 'startsWith', value: 'Old'},
      {type: 'date', operator: 'before', value: '2022-06-01'}
    ],
    active: true
  },
];
