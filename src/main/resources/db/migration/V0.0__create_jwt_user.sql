create table jwt_user
(
    id         bigint auto_increment primary key,
    email      varchar(255) unique not null,
    position   enum (
        'Owner',
        'Receptionist',
        'Teacher',
        'Student'
        ),
    enabled    bit(1),
    password   varchar(255) not null,
    first_name varchar(100),
    last_name  varchar(100),
    username  varchar(255),
    birthday   date,
    uuid       varchar(255)
);
CREATE TABLE jwt_user_role
(
    jwt_user_id bigint       NOT NULL,
    role        varchar(100) NOT NULL,
    PRIMARY KEY (jwt_user_id, role),
    FOREIGN KEY (jwt_user_id) REFERENCES jwt_user (id)
)
