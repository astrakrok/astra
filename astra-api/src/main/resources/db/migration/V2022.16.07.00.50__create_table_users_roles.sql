create table public.users_roles
(
	user_id int unique not null,
	foreign key (user_id) references public.users (id),
	role_id int unique not null,
	foreign key (role_id) references public.roles (id)
);