create table public.subjects_specializations
(
    specialization_id bigint references public.specializations (id) on delete cascade,
    subjects_id       bigint references public.subjects (id) on delete cascade,
    unique (specialization_id, subjects_id)
);
