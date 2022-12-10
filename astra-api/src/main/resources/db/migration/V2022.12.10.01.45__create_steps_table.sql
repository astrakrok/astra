create table public.steps
(
    id    bigserial primary key,
    title varchar(20) not null
);

insert into public.steps(title)
values ('КРОК 1');

alter table public.specializations
    add column step_id bigint;

update public.specializations
set step_id=(select s.id from public.steps s where title = 'КРОК 1');

alter table public.specializations
    alter column step_id set not null,
    add constraint specializations_step_id_fkey foreign key (step_id) references public.steps (id);
