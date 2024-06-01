DROP DATABASE IF EXISTS bookpadtest;
CREATE DATABASE bookpadtest;
USE bookpadtest;

GRANT ALL ON bookpadtest.* TO 'admin'@'localhost';

create table if not exists genres(
                                             id          int auto_increment primary key,
                                             name        varchar(50)  not null,
    description varchar(300) not null
    );

insert into genres (id, name, description)
values  (1, 'Azione', 'Racconta di una missione da compiere, che mette alla prova le capacità di sopravvivenza del protagonista, richiedendo coraggio.'),
        (2, 'Horror', 'Possiede una trama terrificante e macabra, l’ambientazione è necessariamente tenebrosa, buia e spaventosa.'),
        (3, 'Narrativa Storica', 'Racconta vicende inserite in un contesto storico ben preciso, ma i personaggi e la trama possono essere creati liberamente.'),
        (4, 'Romantico', 'Incentrato su una storia d’amore, rigorosamente a lieto fine.');

create table if not exists tags(
    text_tag varchar(500) not null primary key
    );


create table if not exists users(
                                            id       int auto_increment primary key,
                                            username varchar(20)  not null,
    email    varchar(256) not null,
    password varchar(256) not null,
    about    varchar(500) null,
    avatar   longblob     null,
    constraint email unique (email),
    constraint username unique (username)
    );

create table if not exists follow(
                                             ID_who_i_follow int not null,
                                             ID_followed_by  int not null,
                                             primary key (ID_who_i_follow, ID_followed_by),
    foreign key (ID_followed_by) references users (id) on update cascade on delete cascade,
    foreign key (ID_who_i_follow) references users (id) on update cascade on delete cascade
    );

create table if not exists stories(
                                              id         int auto_increment primary key,
                                              title      varchar(50)   not null,
    plot       mediumtext    not null,
    n_like     int default 0 null,
    n_comments int default 0 null,
    n_savings  int default 0 null,
    cover      longblob      null,
    ID_user    int           not null,
    ID_genre   int           not null,
    dt         datetime      null,
    constraint title
    unique (title),
    foreign key (ID_user) references users (id) on update cascade on delete cascade,
    foreign key (ID_genre) references genres (id)
    );

create table if not exists chapters(
    title    varchar(50) not null,
    content  mediumtext  not null,
    n_chap   int         not null,
    ID_story int         not null,
    primary key (n_chap, ID_story),
    foreign key (ID_story) references stories (id) on update cascade on delete cascade
    );

create table if not exists comments(
                                               id       int auto_increment primary key,
                                               content  text not null,
                                               data_cm  date not null,
                                               ID_user  int  not null,
                                               ID_story int  not null,
                                               foreign key (ID_user) references users (id)
    on update cascade on delete cascade,
    foreign key (ID_story) references stories (id)
    on update cascade on delete cascade
    );

create table if not exists has_like(
                                               ID_story int not null,
                                               ID_user  int not null,
                                               primary key (ID_story, ID_user),
    foreign key (ID_user) references users (id) on update cascade on delete cascade,
    foreign key (ID_story) references stories (id) on update cascade on delete cascade
    );

create table if not exists has_tag(
                                      ID_story int         not null,
                                      text_tag varchar(30) not null,
    primary key (ID_story, text_tag),
    foreign key (ID_story) references stories (id) on update cascade on delete cascade,
    foreign key (text_tag) references tags (text_tag) on update cascade on delete cascade
    );

create table if not exists library(
                                              ID_story int not null,
                                              ID_user  int not null,
                                              primary key (ID_story, ID_user),
    foreign key (ID_user) references users (id)
    on update cascade on delete cascade,
    foreign key (ID_story) references stories (id)
    on update cascade on delete cascade
    );


