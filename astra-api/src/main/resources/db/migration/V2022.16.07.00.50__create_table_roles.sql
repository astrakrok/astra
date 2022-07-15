create type role_types as enum('user', 'admin', 'superadmin');

create table roles(
	id bigserial primary key,
	role role_types	
);