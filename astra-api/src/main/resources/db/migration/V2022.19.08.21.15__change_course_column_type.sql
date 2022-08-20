alter table public.users
    alter column course type integer using (course::integer);
