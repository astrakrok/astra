create table public.users
(
    id      bigserial unique primary key,
    name    varchar(20),
    surname varchar(20),
    email   varchar(40) unique not null
);
create table public.roles
(
    id   bigserial unique primary key,
    role varchar(20)
);
create table public.users_roles
(
    user_id int unique not null references public.users (id),
    role_id int not null references public.roles (id),
    unique (user_id, role_id)
);
