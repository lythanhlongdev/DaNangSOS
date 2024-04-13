create table reports
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    history_id  BIGINT,
    role        varchar(20)  not null,
    created_at  datetime     not null,
    description varchar(500) not null,
    FOREIGN KEY (history_id) REFERENCES histories (id)
)
