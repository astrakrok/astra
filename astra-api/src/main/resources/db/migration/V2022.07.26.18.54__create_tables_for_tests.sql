create table public.tests
(
    id       bigserial primary key,
    question varchar not null,
    comment  varchar not null
);

create table public.tests_variants
(
    id          bigserial primary key,
    test_id     bigint references public.tests (id) on delete cascade,
    title       varchar not null,
    explanation varchar not null,
    is_correct  boolean not null
);

create table public.tests_subjects
(
    test_id    bigint references public.tests (id) on delete cascade,
    subject_id bigint references public.subjects (id) on delete cascade,
    unique (test_id, subject_id)
);

create table public.tests_exams
(
    test_id bigint references public.tests (id) on delete cascade,
    exam_id bigint references public.exams (id) on delete cascade,
    unique (test_id, exam_id)
)
