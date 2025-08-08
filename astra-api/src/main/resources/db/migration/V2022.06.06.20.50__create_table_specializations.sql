create table if not exists public.specializations
(
    id    int primary key generated always as identity,
    title varchar(255)
);
