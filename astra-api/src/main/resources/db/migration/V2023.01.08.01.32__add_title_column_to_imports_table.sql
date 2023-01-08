alter table public.imports
    add column if not exists title varchar;

update public.imports
set title='non-null value';

alter table public.imports
    alter column title set not null;
