create table public.users
(
    id      bigserial primary key,
    name    varchar(50)  not null,
    surname varchar(50)  not null,
    email   varchar(50)  not null,
    course  varchar(10)  not null,
    school  varchar(255) not null
);

create table public.roles
(
    id    bigserial primary key,
    title varchar(20) not null
);

create table public.users_roles
(
    id      bigserial primary key,
    user_id bigint not null references public.users (id),
    role_id bigint not null references public.roles (id),
    unique (user_id, role_id)
);

insert into public.roles(title)
values ('USER'),
       ('ADMIN'),
       ('SUPER_ADMIN');
