import {Filter} from '../types/filter';

export const SAMPLE_FILTERS: Filter[] = [
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d479",
    name: "Adult Users Only",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440001", type: 'number', operator: 'gte', value: 18 }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d480",
    name: "Premium Products Search",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440002", type: 'string', operator: 'contains', value: 'premium', caseSensitive: false },
      { id: "550e8400-e29b-41d4-a716-446655440003", type: 'number', operator: 'gt', value: 100 }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d481",
    name: "Recent High-Value Orders",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440004", type: 'date', operator: 'after', value: new Date('2024-01-01') },
      { id: "550e8400-e29b-41d4-a716-446655440005", type: 'number', operator: 'gte', value: 500 },
      { id: "550e8400-e29b-41d4-a716-446655440006", type: 'string', operator: 'eq', value: 'completed', caseSensitive: false }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d482",
    name: "Senior Employees",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440007", type: 'string', operator: 'startsWith', value: 'Sr.', caseSensitive: false },
      { id: "550e8400-e29b-41d4-a716-446655440008", type: 'date', operator: 'before', value: new Date('2020-01-01') },
      { id: "550e8400-e29b-41d4-a716-446655440009", type: 'number', operator: 'gte', value: 75000 }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d483",
    name: "Urgent Tasks This Week",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440010", type: 'string', operator: 'contains', value: 'urgent', caseSensitive: false },
      { id: "550e8400-e29b-41d4-a716-446655440011", type: 'date', operator: 'before', value: new Date('2025-09-28') },
      { id: "550e8400-e29b-41d4-a716-446655440012", type: 'number', operator: 'lte', value: 3 }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d484",
    name: "Gmail Users",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440013", type: 'string', operator: 'endsWith', value: '@gmail.com', caseSensitive: false }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d485",
    name: "Budget Range Filter",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440014", type: 'number', operator: 'gte', value: 1000 },
      { id: "550e8400-e29b-41d4-a716-446655440015", type: 'number', operator: 'lte', value: 5000 }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d486",
    name: "This Year's Records",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440016", type: 'date', operator: 'after', value: new Date('2025-01-01') },
      { id: "550e8400-e29b-41d4-a716-446655440017", type: 'date', operator: 'before', value: new Date('2026-01-01') }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d487",
    name: "VIP Customer Profile",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440018", type: 'string', operator: 'eq', value: 'VIP', caseSensitive: true },
      { id: "550e8400-e29b-41d4-a716-446655440019", type: 'number', operator: 'gt', value: 10000 },
      { id: "550e8400-e29b-41d4-a716-446655440020", type: 'date', operator: 'before', value: new Date('2023-01-01') },
      { id: "550e8400-e29b-41d4-a716-446655440021", type: 'string', operator: 'contains', value: 'gold', caseSensitive: false }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d488",
    name: "Overdue Invoices",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440022", type: 'date', operator: 'before', value: new Date() },
      { id: "550e8400-e29b-41d4-a716-446655440023", type: 'string', operator: 'eq', value: 'unpaid', caseSensitive: false },
      { id: "550e8400-e29b-41d4-a716-446655440024", type: 'number', operator: 'gt', value: 0 }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d489",
    name: "Today's Appointments",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440025", type: 'date', operator: 'equals', value: new Date('2025-09-21') }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d490",
    name: "Discounted Items",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440026", type: 'number', operator: 'lt', value: 50 }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d491",
    name: "Tech Department Search",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440027", type: 'string', operator: 'contains', value: 'tech', caseSensitive: false },
      { id: "550e8400-e29b-41d4-a716-446655440028", type: 'string', operator: 'contains', value: 'engineer', caseSensitive: false },
      { id: "550e8400-e29b-41d4-a716-446655440029", type: 'string', operator: 'contains', value: 'developer', caseSensitive: false }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d492",
    name: "Q4 Financial Report",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440030", type: 'date', operator: 'after', value: new Date('2024-10-01') },
      { id: "550e8400-e29b-41d4-a716-446655440031", type: 'date', operator: 'before', value: new Date('2024-12-31') },
      { id: "550e8400-e29b-41d4-a716-446655440032", type: 'number', operator: 'gte', value: 1000000 },
      { id: "550e8400-e29b-41d4-a716-446655440033", type: 'string', operator: 'eq', value: 'approved', caseSensitive: false },
      { id: "550e8400-e29b-41d4-a716-446655440034", type: 'number', operator: 'lte', value: 5000000 }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d493",
    name: "Critical Bug Reports",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440035", type: 'string', operator: 'startsWith', value: 'CRITICAL', caseSensitive: true },
      { id: "550e8400-e29b-41d4-a716-446655440036", type: 'date', operator: 'after', value: new Date('2025-09-01') },
      { id: "550e8400-e29b-41d4-a716-446655440037", type: 'number', operator: 'eq', value: 1 },
      { id: "550e8400-e29b-41d4-a716-446655440038", type: 'string', operator: 'contains', value: 'production', caseSensitive: false }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d494",
    name: "Low Stock Alert",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440039", type: 'number', operator: 'lte', value: 10 },
      { id: "550e8400-e29b-41d4-a716-446655440040", type: 'string', operator: 'eq', value: 'active', caseSensitive: false },
      { id: "550e8400-e29b-41d4-a716-446655440041", type: 'date', operator: 'after', value: new Date('2025-01-01') }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d495",
    name: "New Student Enrollment",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440042", type: 'string', operator: 'eq', value: 'enrolled', caseSensitive: false },
      { id: "550e8400-e29b-41d4-a716-446655440043", type: 'date', operator: 'after', value: new Date('2025-09-01') },
      { id: "550e8400-e29b-41d4-a716-446655440044", type: 'number', operator: 'gte', value: 16 },
      { id: "550e8400-e29b-41d4-a716-446655440045", type: 'number', operator: 'lte', value: 25 }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d496",
    name: "Mobile App Analytics",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440046", type: 'string', operator: 'contains', value: 'mobile', caseSensitive: false },
      { id: "550e8400-e29b-41d4-a716-446655440047", type: 'date', operator: 'after', value: new Date('2025-08-01') },
      { id: "550e8400-e29b-41d4-a716-446655440048", type: 'number', operator: 'gte', value: 1000 },
      { id: "550e8400-e29b-41d4-a716-446655440049", type: 'string', operator: 'endsWith', value: '_mobile', caseSensitive: true },
      { id: "550e8400-e29b-41d4-a716-446655440050", type: 'number', operator: 'lte', value: 50000 }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d497",
    name: "Weekend Bookings",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440051", type: 'date', operator: 'after', value: new Date('2025-09-20') },
      { id: "550e8400-e29b-41d4-a716-446655440052", type: 'date', operator: 'before', value: new Date('2025-09-21') },
      { id: "550e8400-e29b-41d4-a716-446655440053", type: 'string', operator: 'eq', value: 'confirmed', caseSensitive: false },
      { id: "550e8400-e29b-41d4-a716-446655440054", type: 'number', operator: 'gte', value: 2 }
    ]
  },
  {
    id: "f47ac10b-58cc-4372-a567-0e02b2c3d498",
    name: "Enterprise Content Filter",
    criteria: [
      { id: "550e8400-e29b-41d4-a716-446655440055", type: 'string', operator: 'startsWith', value: 'ENT-', caseSensitive: true },
      { id: "550e8400-e29b-41d4-a716-446655440056", type: 'string', operator: 'contains', value: 'enterprise', caseSensitive: false },
      { id: "550e8400-e29b-41d4-a716-446655440057", type: 'date', operator: 'after', value: new Date('2024-01-01') },
      { id: "550e8400-e29b-41d4-a716-446655440058", type: 'number', operator: 'gte', value: 5 },
      { id: "550e8400-e29b-41d4-a716-446655440059", type: 'string', operator: 'endsWith', value: '.pdf', caseSensitive: false }
    ]
  }
];
