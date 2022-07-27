create table image
(
    id  int primary key generated by default as identity,
    uri varchar unique
)

create table person
(
    id       int primary key generated by default as identity,
    username varchar not null,
    password varchar not null,
    email    varchar not null,
    image_id int     not null references image (id) on delete set null,
    status   int     not null,
    updated_at timestamp
)