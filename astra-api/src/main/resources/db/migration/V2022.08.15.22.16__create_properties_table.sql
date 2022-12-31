create table public.properties
(
    id    bigserial primary key,
    name  varchar not null,
    value varchar
);

insert into public.properties(name, value)
values ('examinationThresholdPercentage', '62');
