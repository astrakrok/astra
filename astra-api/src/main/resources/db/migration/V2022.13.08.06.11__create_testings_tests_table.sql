create table public.testings_tests
(
    id         bigserial primary key,
    testing_id bigint references public.testings (id) on delete cascade,
    test_id    bigint references public.tests (id) on delete cascade,
    unique (testing_id, test_id)
);