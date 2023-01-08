create table if not exists public.imports
(
    id           bigserial primary key,
    source       varchar not null,
    source_title varchar not null,
    details      jsonb,
    created_at   timestamp without time zone default (now() at time zone 'utc')
);

create table if not exists public.import_tests
(
    id        bigserial primary key,
    import_id bigint references public.imports (id) not null,
    test_id   bigint references public.tests (id)   not null,
    details   jsonb                                 not null
);
