create table public.examinations_answers
(
    id             bigserial primary key,
    examination_id bigint references public.examinations (id) on delete cascade,
    test_id        bigint references public.tests (id) on delete cascade not null,
    variant_id     bigint references public.tests_variants (id) on delete cascade,
    unique (examination_id, test_id)
);