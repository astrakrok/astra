create table if not exists public.examinations
(
    id                bigserial primary key,
    user_id           bigint not null references public.users (id) on delete cascade,
    specialization_id bigint not null references public.specializations (id) on delete cascade,
    exam_id           bigint not null references public.exams (id) on delete cascade,
    finished_at       timestamp
);
