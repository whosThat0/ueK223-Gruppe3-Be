--USERS
insert into users (id, email,first_name,last_name, password)
values ('ba804cb9-fa14-42a5-afaf-be488742fc54', 'admin@example.com', 'James','Bond', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6' ), -- Password: 1234
       ('0d8fa44c-54fd-4cd0-ace9-2a7da57992de', 'user@example.com', 'Tyler','Durden', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6'), -- Password: 1234
       ('0d8fa34c-54fd-4cd0-ace9-2a7da57992de', 'extrauser@example.com', 'Sønny', 'Kitty#1', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6')-- Password: 1234
    ON CONFLICT DO NOTHING;

insert into users (id, email, first_name, last_name, password)
values ('11111111-2222-3333-4444-555555555555', 'nogroups@example.com', 'Bob', 'NoGroups',
        '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6') -- Password: 1234
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
       ('abcd1234-5678-90ef-1234-567890abcdef', 'GROUP_DELETE'),
       ('11111111-1111-1111-1111-111111111111', 'GROUP_JOIN')
    ON CONFLICT DO NOTHING;

-- assign roles to users
INSERT INTO users_role (users_id, role_id)
VALUES ('ba804cb9-fa14-42a5-afaf-be488742fc54', 'ab505c92-7280-49fd-a7de-258e618df074'), -- admin@example.com → ADMIN
       ('0d8fa44c-54fd-4cd0-ace9-2a7da57992de', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02'), -- user@example.com → USER
       ('0d8fa34c-54fd-4cd0-ace9-2a7da57992de', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02'),  -- extrauser@example.com → USER
       ('0d8fa44c-54fd-4cd0-ace9-2a7da57992de', 'd29e709c-0ff1-4f4c-a7ef-09f656c390f1'), -- user@example.com → DEFAULT
       ('11111111-2222-3333-4444-555555555555', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02') -- nogroups@example.com → USER
    ON CONFLICT DO NOTHING;


--assign authorities to roles
INSERT INTO role_authority(role_id, authority_id)
VALUES ('d29e709c-0ff1-4f4c-a7ef-09f656c390f1', '2ebf301e-6c61-4076-98e3-2a38b31daf86'), -- DEFAULT → DEFAULT
       ('ab505c92-7280-49fd-a7de-258e618df074', '76d2cbf6-5845-470e-ad5f-2edb9e09a868'), -- ADMIN → USER_MODIFY
       ('c6aee32d-8c35-4481-8b3e-a876a39b0c02', '21c942db-a275-43f8-bdd6-d048c21bf5ab'), -- USER → USER_DEACTIVATE
       ('ab505c92-7280-49fd-a7de-258e618df074', 'abcd1234-5678-90ef-1234-567890abcdef'), -- ADMIN → GROUP_DELETE
       ('ab505c92-7280-49fd-a7de-258e618df074', 'e7c3f0a8-1234-4a56-8901-abcdef123456'),-- ADMIN → GROUP_CREATE
       ('c6aee32d-8c35-4481-8b3e-a876a39b0c02', '11111111-1111-1111-1111-111111111111') -- USER → GROUP_JOIN
    ON CONFLICT DO NOTHING;

-- GROUPS (Existing Data)
INSERT INTO groups (id, name, motto, logo, administrator_id)
VALUES
    ('f8dcb318-1f2a-4e17-a000-16c5587887c1', 'TestInOrdnerToTestJoin', 'Hello', 'https://www.noser.com/wp-content/uploads/2018/09/Noser-Young-neu.jpg', 'ba804cb9-fa14-42a5-afaf-be488742fc54'), -- Admin is James Bond
    ('22222222-3333-4444-5555-666666666666', 'Fight Club', 'The first rule...', 'https://upload.wikimedia.org/wikipedia/en/f/fc/Fight_Club_poster.jpg', '0d8fa44c-54fd-4cd0-ace9-2a7da57992de'), -- Tyler is admin
    ('33333333-4444-5555-6666-777777777777', 'Gamers United', 'Respawn. Retry. Dominate.', 'https://cdn-icons-png.flaticon.com/512/69/69881.png', '0d8fa34c-54fd-4cd0-ace9-2a7da57992de') -- Extrauser is admin
    ON CONFLICT DO NOTHING;

-- GROUPS (10 New Mock Data)
INSERT INTO groups (id, name, motto, logo, administrator_id)
VALUES
    (gen_random_uuid(), 'Bookworms Society', 'Lost in the pages.', 'https://example.com/logos/books.png', '0d8fa44c-54fd-4cd0-ace9-2a7da57992de'),
    (gen_random_uuid(), 'Code Commanders', 'Write clean code or die trying.', 'https://example.com/logos/coding.png', 'ba804cb9-fa14-42a5-afaf-be488742fc54'),
    (gen_random_uuid(), 'Hiking Heroes', 'Life is better on the trails.', 'https://example.com/logos/hiking.png', '0d8fa34c-54fd-4cd0-ace9-2a7da57992de'),
    (gen_random_uuid(), 'Local Foodies', 'Eating our way through the city.', 'https://example.com/logos/food.png', '0d8fa44c-54fd-4cd0-ace9-2a7da57992de'),
    (gen_random_uuid(), 'Movie Buffs Club', 'Reel talk starts now.', 'https://example.com/logos/movie.png', 'ba804cb9-fa14-42a5-afaf-be488742fc54'),
    (gen_random_uuid(), 'Startup Founders Network', 'Disrupting everything.', 'https://example.com/logos/startup.png', '0d8fa34c-54fd-4cd0-ace9-2a7da57992de'),
    (gen_random_uuid(), 'Photography Enthusiasts', 'Capturing the perfect moment.', 'https://example.com/logos/camera.png', '0d8fa44c-54fd-4cd0-ace9-2a7da57992de'),
    (gen_random_uuid(), 'Vinyl Record Collectors', 'Spinning the classics.', 'https://example.com/logos/vinyl.png', 'ba804cb9-fa14-42a5-afaf-be488742fc54'),
    (gen_random_uuid(), 'Yoga & Meditation Group', 'Find your inner peace.', 'https://example.com/logos/yoga.png', '0d8fa34c-54fd-4cd0-ace9-2a7da57992de'),
    (gen_random_uuid(), 'The Green Thumbs', 'Cultivating a better world.', 'https://example.com/logos/garden.png', '0d8fa44c-54fd-4cd0-ace9-2a7da57992de')
    ON CONFLICT DO NOTHING;


INSERT INTO group_members (group_id, user_id)
VALUES
    ('22222222-3333-4444-5555-666666666666', '0d8fa44c-54fd-4cd0-ace9-2a7da57992de'), -- Tyler is member of Fight Club (and admin)
    ('33333333-4444-5555-6666-777777777777', '0d8fa34c-54fd-4cd0-ace9-2a7da57992de') -- Extrauser is member of Gamers United (and admin)
    ON CONFLICT DO NOTHING;