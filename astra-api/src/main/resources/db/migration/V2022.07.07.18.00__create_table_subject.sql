create table public.subjects
(
    id                int primary key generated always as identity,
    title             varchar(255),
    specialization_id int not null,
    foreign key (specialization_id) references public.specializations (id)
);
