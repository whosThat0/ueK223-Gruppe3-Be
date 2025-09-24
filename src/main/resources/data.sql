--USERS

-- you can user gen_random_uuid () to generate random IDs, use this only to generate testdata


insert into users (id, email,first_name,last_name, password)
values ('ba804cb9-fa14-42a5-afaf-be488742fc54', 'admin@example.com', 'James','Bond', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6' ), -- Password: 1234
('0d8fa44c-54fd-4cd0-ace9-2a7da57992de', 'user@example.com', 'Tyler','Durden', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6'), -- Password: 1234
('0d8fa34c-54fd-4cd0-ace9-2a7da57992de', 'extrauser@example.com', 'Sønny', 'Spëcial#1', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6')-- Password: 1234
 ON CONFLICT DO NOTHING;

--ROLES
INSERT INTO  role(id, name)
VALUES ('d29e709c-0ff1-4f4c-a7ef-09f656c390f1', 'DEFAULT'),
('ab505c92-7280-49fd-a7de-258e618df074', 'ADMIN'),
('c6aee32d-8c35-4481-8b3e-a876a39b0c02', 'USER')
ON CONFLICT DO NOTHING;

--AUTHORITIES
INSERT INTO authority(id, name)
VALUES ('2ebf301e-6c61-4076-98e3-2a38b31daf86', 'DEFAULT'),
('76d2cbf6-5845-470e-ad5f-2edb9e09a868', 'USER_MODIFY'),
('21c942db-a275-43f8-bdd6-d048c21bf5ab', 'USER_DEACTIVATE'),
('e7c3f0a8-1234-4a56-8901-abcdef123456', 'GROUP_CREATE'),
('abcd1234-5678-90ef-1234-567890abcdef', 'GROUP_DELETE')
ON CONFLICT DO NOTHING;

-- assign roles to users
INSERT INTO users_role (users_id, role_id)
VALUES ('ba804cb9-fa14-42a5-afaf-be488742fc54', 'ab505c92-7280-49fd-a7de-258e618df074'),
    ('ba804cb9-fa14-42a5-afaf-be488742fc54', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02'),
    ('ba804cb9-fa14-42a5-afaf-be488742fc54', 'd29e709c-0ff1-4f4c-a7ef-09f656c390f1'),
    ('0d8fa44c-54fd-4cd0-ace9-2a7da57992de', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02'), -- user@example.com → USER
    ('0d8fa34c-54fd-4cd0-ace9-2a7da57992de', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02'),  -- extrauser@example.com → USER
('0d8fa44c-54fd-4cd0-ace9-2a7da57992de', 'd29e709c-0ff1-4f4c-a7ef-09f656c390f1')
ON CONFLICT DO NOTHING;



--assign authorities to roles
INSERT INTO role_authority(role_id, authority_id)
VALUES ('d29e709c-0ff1-4f4c-a7ef-09f656c390f1', '2ebf301e-6c61-4076-98e3-2a38b31daf86'),
('ab505c92-7280-49fd-a7de-258e618df074', '76d2cbf6-5845-470e-ad5f-2edb9e09a868'),
('c6aee32d-8c35-4481-8b3e-a876a39b0c02', '21c942db-a275-43f8-bdd6-d048c21bf5ab'),
('ab505c92-7280-49fd-a7de-258e618df074', 'abcd1234-5678-90ef-1234-567890abcdef'),
('ab505c92-7280-49fd-a7de-258e618df074', 'e7c3f0a8-1234-4a56-8901-abcdef123456') -- GROUP_CREATE

 ON CONFLICT DO NOTHING;

INSERT INTO groups (id, name, motto, logo, administrator_id)
VALUES
    ('f8dcb318-1f2a-4e17-a000-16c5587887c1', 'TestInOrdnerToTestJoin', 'Hello', 'https://www.noser.com/wp-content/uploads/2018/09/Noser-Young-neu.jpg', 'ba804cb9-fa14-42a5-afaf-be488742fc54')
ON CONFLICT DO NOTHING;

insert into users (id, email, first_name, last_name, password)
values ('11111111-2222-3333-4444-555555555555', 'nogroups@example.com', 'Bob', 'NoGroups',
        '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6') -- Password: 1234
    ON CONFLICT DO NOTHING;

-- assign him only the USER role (no ADMIN)
insert into users_role (users_id, role_id)
values ('11111111-2222-3333-4444-555555555555', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02')
    ON CONFLICT DO NOTHING;

