create table public.users
(
    id          serial
        primary key,
    create_date timestamp(6) not null,
    email       varchar(255) not null,
    enabled     boolean      not null,
    first_name  varchar(100) not null,
    last_name   varchar(100) not null,
    modify_date timestamp(6),
    password    varchar(100) not null,
    role        varchar(255) not null
        constraint users_role_check
            check ((role)::text = ANY ((ARRAY ['USER'::character varying, 'ADMIN'::character varying])::text[])),
    username    varchar(100) not null
);

alter table public.users
    owner to admin;

create index indexenabled
    on public.users (enabled);

create index multiindexemailenabled
    on public.users (email, enabled);

create index multiindexusernameenabled
    on public.users (username, enabled);

INSERT INTO public.users (id,username,password,email,first_name,last_name,role,enabled,create_date) VALUES (1,'admin','$2a$10$sNsZ9JpJ9dzeEzfL3U0wpuVoCbIl/BTdn8PT.PPslKmbqiYhJfVT.','admin@gmail.com','admin','admin','ADMIN',true, NOW());

