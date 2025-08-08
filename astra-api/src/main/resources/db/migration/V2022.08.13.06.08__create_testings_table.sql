create table if not exists public.testings
(
    id                bigserial primary key,
    exam_id           bigint references public.exams (id) on delete cascade,
    specialization_id bigint references public.specializations (id) on delete cascade,
    unique (exam_id, specialization_id)
);
