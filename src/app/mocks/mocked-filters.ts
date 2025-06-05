import {Filters} from '../shared/filter-schemas-and-types';

export const MOCKED_FILTERS: Filters = [
  {
    id: 'a3f9e730-8bde-4c72-b3fa-bb2f3aab0c2d',
    name: 'High Value Transactions',
    conditions: [{type: 'amount', operator: '>', value: 1000}],
    active: true
  },
  {
    id: 'b1d68c12-4ebd-4c9e-9f6c-df62c3693db1',
    name: 'Recent Purchases',
    conditions: [{type: 'date', operator: 'after', value: '2025-01-01'}],
    active: true
  },
  {
    id: 'f4c27a5e-00c4-4ac1-9f60-376e09f56f59',
    name: 'Title Starts With A',
    conditions: [{type: 'title', operator: 'startsWith', value: 'A'}],
    active: false
  },
  {
    id: '65c858e3-d08f-4b9e-96d2-4f98b23d2130',
    name: 'Exact Amount Match',
    conditions: [{type: 'amount', operator: '=', value: 500}],
    active: false
  },
  {
    id: '8e79285b-f30e-4e1e-a269-f4c55e34f21b',
    name: 'Old Records',
    conditions: [{type: 'date', operator: 'before', value: '2023-01-01'}],
    active: true
  },
];
