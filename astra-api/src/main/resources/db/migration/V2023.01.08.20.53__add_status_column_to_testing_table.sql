alter table public.testings
    add column if not exists status varchar;

update public.testings
set status='DRAFT';

update public.testings t
set status='ACTIVE'
where exists(select 1 from public.examinations e where e.testing_id = t.id);

alter table public.testings
    alter column status set not null;
