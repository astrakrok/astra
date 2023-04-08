alter table public.import_tests
    alter column details set default '{}'::jsonb;
