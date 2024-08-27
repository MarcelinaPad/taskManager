create table users
(
    id       bigint auto_increment
        primary key,
    password varchar(255) null,
    username varchar(255) null,
    email    varchar(255) not null,
    constraint UK_r43af9ap4edm43mmtq01oddj6
        unique (username),
    constraint users_pk
        unique (email)
);

create table task
(
    id          bigint auto_increment
        primary key,
    created_at  datetime(6)  null,
    description varchar(255) null,
    status      varchar(255) null,
    title       varchar(255) null,
    category    varchar(255) null,
    due_date    date         null,
    priority    varchar(255) null,
    user_id     bigint       not null,
    constraint FKbhwpp8tr117vvbxhf5sbkdkc9
        foreign key (user_id) references users (id)
);
