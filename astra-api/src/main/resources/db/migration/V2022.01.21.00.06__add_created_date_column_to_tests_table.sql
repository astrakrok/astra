alter table public.tests
    add column if not exists created_date timestamp without time zone not null default (now() at time zone 'utc');
