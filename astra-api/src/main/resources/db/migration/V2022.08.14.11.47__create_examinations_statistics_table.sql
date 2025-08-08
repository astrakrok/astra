create table if not exists public.examinations_statistics
(
    id              bigserial primary key,
    testing_id      bigint   not null references public.testings (id) on delete cascade,
    user_id         bigint   not null references public.users (id) on delete cascade,
    last_percentage smallint not null,
    best_percentage smallint not null
)
