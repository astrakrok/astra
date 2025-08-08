create table if not exists public.exams
(
    id    bigserial primary key,
    title varchar(255) not null
);
