create table categories (
    id bigserial primary key,
    title varchar(255)
);

create table authors (
    id bigserial primary key,
    full_name varchar(255)
);

create table books (
    id bigserial primary key,
    title varchar(255),
    genre varchar(255),
    author_id bigint,
    foreign key (author_id) references authors (id)
);

create table books_details (
    id bigserial primary key,
    book_id bigint,
    description varchar(255),
    foreign key (book_id) references books (id)
);

create table reviews (
    id bigserial primary key,
    author varchar(255),
    text varchar(255),
    grade bigint,
    date_added date,
    book_id bigint,
    foreign key (book_id) references books (id)
);

insert into categories (title) values ('Категория 1'), ('Категория 2'), ('Категория 3');

insert into authors (full_name) values ('Толкиен'), ('Роулинг'), ('Сандерсон'), ('Азимов'), ('Мартин');

insert into books (title, author_id, genre) values
  ('Властелин колец: Братство кольца', 1, 'FANTASY'),
  ('Гарри Поттер и Философский камень', 2, 'FANTASY'),
  ('Рожденный туманом: Пепел и сталь', 3, 'FANTASY'),
  ('Рожденный туманом: Источник вознесения', 3, 'FANTASY'),
  ('Рожденный туманом: Герой веков', 3, 'FANTASY'),
  ('Архив Буресвета: Путь королей', 3, 'FANTASY'),
  ('Академия', 4, 'SCIFI'),
  ('Битва королей', 5 ,'FANTASY'),
  ('Буря мечей', 5 ,'FANTASY'),
  ('Пир стервятников', 5 ,'FANTASY'),
  ('Танец с драконами', 5 ,'FANTASY');

insert into reviews (book_id, author, text, grade, date_added) values
  (1, 'Manuel', 'good', 9, '2023-12-18'),
  (1, 'Steven', 'bad', 1, '2023-12-18'),
  (2, 'Steven', 'good', 8, '2023-12-18'),
  (3, 'Vincent', 'good', 8, '2023-12-18'),
  (4, 'Alan', 'good', 4, '2023-12-18'),
  (5, 'Arnold', 'good', 5, '2023-12-18'),
  (6, 'Bonnie', 'good', 6, '2023-12-18'),
  (7, 'Patricia', 'good', 7, '2023-12-18'),
  (8, 'Anna', 'good', 6, '2023-12-18'),
  (9, 'Lera', 'good', 4, '2023-12-18'),
  (10, 'Igor', 'good', 5, '2023-12-18'),
  (11, 'Artem', 'good', 3, '2023-12-18');

insert into books_details (book_id, description) values
  (1, 'Книга про Властелина колец'),
  (2, 'Книга про Гарри Поттера'),
  (3, 'Книга про Рожденного туманом'),
  (4, 'Книга про Рожденного туманом'),
  (5, 'Книга про Рожденного туманом'),
  (6, 'Книга про Архив Буресвета'),
  (7, 'Продолжение игры престолов'),
  (8, 'Продолжение игры престолов'),
  (9, 'Продолжение игры престолов'),
  (10, 'Продолжение игры престолов'),
  (11, 'Продолжение игры престолов');
