alter table public.users
    add column specialization_id bigint references public.specializations (id);
