create table if not exists public.test
(
    id   bigserial primary key,
    name varchar not null
);

insert into test(name) values ('Andrii');