alter table public.tests
    add column question_svg varchar,
    add column comment_svg  varchar;

alter table public.tests_variants
    add column title_svg       varchar,
    add column explanation_svg varchar;
