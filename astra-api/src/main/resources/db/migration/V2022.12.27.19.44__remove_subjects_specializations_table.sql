alter table public.subjects
    add column if not exists specialization_id bigint
        constraint subjects_specialization_id_fkey REFERENCES public.specializations (id) ON UPDATE CASCADE ON DELETE CASCADE;

update public.subjects
set specialization_id = (select ss.specialization_id
                         from public.subjects_specializations ss
                         where ss.subject_id = id
                         limit 1);

drop table public.subjects_specializations;
