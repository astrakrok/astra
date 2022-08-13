delete
from public.examinations_answers;

delete
from public.examinations;

alter table public.examinations
    drop column specialization_id,
    drop column exam_id,
    add column testing_id bigint references public.testings (id) not null;
