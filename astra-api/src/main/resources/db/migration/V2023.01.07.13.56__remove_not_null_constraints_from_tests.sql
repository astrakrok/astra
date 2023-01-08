alter table public.tests
    alter column question drop not null,
    alter column comment drop not null;

alter table public.tests_variants
    alter column title drop not null,
    alter column explanation drop not null;

alter table public.tests
    add column if not exists status varchar;

update public.tests
set status='ACTIVE';

alter table public.tests
    alter column status set not null;
