INSERT INTO filters (id, name, active) VALUES
    ('b3035773-0d5b-4c07-8e6c-7e50b1c9c7f9', 'Active Filter with All Types', TRUE);

INSERT INTO filters (id, name, active) VALUES
    ('e842f1f0-a1c2-4a7b-8d6e-9f0a1b2c3d4e', 'Inactive Filter with Diverse Conditions', FALSE);


INSERT INTO filter_conditions (id, filter_id, condition_type, condition_details) VALUES
    ('c7a8b9c0-d1e2-3f4a-5b6c-7d8e9f0a1b2c', 'b3035773-0d5b-4c07-8e6c-7e50b1c9c7f9', 'amount', '{"operator": ">=", "value": 100.00}');
INSERT INTO filter_conditions (id, filter_id, condition_type, condition_details) VALUES
    ('d4e5f6g7-h8i9-j0k1-l2m3-n4o5p6q7r8s9', 'b3035773-0d5b-4c07-8e6c-7e50b1c9c7f9', 'title', '{"operator": "contains", "value": "Important"}');
INSERT INTO filter_conditions (id, filter_id, condition_type, condition_details) VALUES
    ('f0g1h2i3-j4k5-l6m7-n8o9-p0q1r2s3t4u5', 'b3035773-0d5b-4c07-8e6c-7e50b1c9c7f9', 'date', '{"operator": "after", "value": "2024-01-01T00:00:00Z"}');

INSERT INTO filter_conditions (id, filter_id, condition_type, condition_details) VALUES
    ('a1b2c3d4-e5f6-7g8h-9i0j-k1l2m3n4o5p6', 'e842f1f0-a1c2-4a7b-8d6e-9f0a1b2c3d4e', 'amount', '{"operator": "<", "value": 50.00}');
INSERT INTO filter_conditions (id, filter_id, condition_type, condition_details) VALUES
    ('b2c3d4e5-f6g7-8h9i-0j1k-l2m3n4o5p6q7', 'e842f1f0-a1c2-4a7b-8d6e-9f0a1b2c3d4e', 'title', '{"operator": "startsWith", "value": "Draft"}');
INSERT INTO filter_conditions (id, filter_id, condition_type, condition_details) VALUES
    ('c3d4e5f6-g7h8-9i0j-k1l2-m3n4o5p6q7r8', 'e842f1f0-a1c2-4a7b-8d6e-9f0a1b2c3d4e', 'date', '{"operator": "before", "value": "2023-12-31T23:59:59Z"}');
