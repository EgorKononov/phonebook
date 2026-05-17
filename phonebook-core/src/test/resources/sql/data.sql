INSERT INTO companies (id, name)
VALUES (1, 'Google'),
       (2, 'Amazon'),
       (3, 'Apple');
SELECT SETVAL('companies_id_seq', (SELECT MAX(id) FROM companies));

INSERT INTO contacts (id, phone_number, first_name, last_name, company_id, birth_date, email)
VALUES (1, '89131234567', 'Ivan', 'Ivanov', (SELECT id FROM companies WHERE name = 'Google'), '1990-01-10', 'ivan@gmail.com'),
       (2, '89171234568', 'Petr', 'Petrov', (SELECT id FROM companies WHERE name = 'Amazon'), '1995-10-19', 'petr@gmail.com'),
       (3, '89141234569', 'Sveta', 'Svetikova', (SELECT id FROM companies WHERE name = 'Amazon'), '2001-12-23', 'sveta@gmail.com'),
       (4, '89161234510', 'Vlad', 'Vladikov', (SELECT id FROM companies WHERE name = 'Google'), '1984-03-14', 'vlad@gmail.com'),
       (5, '89191234511', 'Anton', 'Antonov', (SELECT id FROM companies WHERE name = 'Amazon'), '1983-05-18', 'anton@gmail.com');
SELECT SETVAL('contacts_id_seq', (SELECT MAX(id) FROM contacts));