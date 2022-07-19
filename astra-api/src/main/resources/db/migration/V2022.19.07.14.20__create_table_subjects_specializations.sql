create table public.subjects_specializations
(
    specialization_id int references specializations (id) on delete cascade,
    subjects_id       int references subjects (id) on delete cascade,
    unique (specialization_id, subjects_id)
);