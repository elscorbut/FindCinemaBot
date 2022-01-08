--liquibase formatted sql

--changeset admin:20211017-1 splitStatements:false stripComments:false
create table if not exists public.t_directory (
                                    id int8 not null,
                                    name varchar(255) not null,
                                    display_name varchar(255) not null,
                                    parent_id int8,
                                    primary key (id)
);

create sequence if not exists public.t_directory_id_seq start 1 increment 1;

alter table if exists public.t_directory
    add constraint directory_parent_id_fk
        foreign key (parent_id)
            references public.t_directory (id);

comment on table public.t_directory is 'Справочники';
comment on column public.t_directory.name is 'Ключ справочника';
comment on column public.t_directory.display_name is 'Наименование справочника (описание)';
comment on column public.t_directory.parent_id is 'Ссылка на родительский справочник';

--changeset admin:20211017-2 splitStatements:false stripComments:false
create table if not exists public.t_directory_value (
                                          id int8 not null,
                                          key varchar(255),
                                          value varchar(255),
                                          short_value varchar(255),
                                          directory int8,
                                          priority integer default 0,
                                          hidden boolean default false,
                                          parent int8,
                                          primary key (id)
);

create sequence if not exists public.t_directory_value_id_seq start 1 increment 1;

alter table if exists public.t_directory_value
    add constraint directory_value_directory_fk
        foreign key (directory)
            references public.t_directory (id);

alter table if exists public.t_directory_value
    add constraint directory_value_parent_fk
        foreign key (parent)
            references public.t_directory_value (id);

comment on table public.t_directory_value is 'Справочные значения';
comment on column public.t_directory_value.directory is 'Ссылка на справочник';
comment on column public.t_directory_value.key is 'Ключ справочного значения';
comment on column public.t_directory_value.value is 'Справочное значение (для вывода на интерфейс)';
comment on column public.t_directory_value.short_value is 'Сокращенное справочное значение (для вывода на интерфейс)';
comment on column public.t_directory_value.hidden is 'Скрытое значение (true = не показывать значение на интерфейсе)';
comment on column public.t_directory_value.priority is 'Приоритет (порядок отображения на интерфейсе)';
comment on column public.t_directory_value.parent is 'Ссылка на родительское справочное значение';

--changeset admin:20211017-3 splitStatements:false stripComments:false
create table if not exists public.t_movie (
                                id int8 not null,
                                rus_name varchar(255),
                                eng_name varchar(255),
                                year int4,
                                kp_rating float8,
                                imdb_rating float8,
                                duration int4,
                                kp_link varchar(255),
                                poster_id varchar(255),
                                country_id int8,
                                director_id int8,
                                type_id int8,
                                deleted boolean default false,
                                primary key (id)
);

alter table if exists public.t_movie
    add constraint movie_country_id_fk
        foreign key (country_id)
            references public.t_directory_value (id);

alter table if exists public.t_movie
    add constraint movie_director_id_fk
        foreign key (director_id)
            references public.t_directory_value (id);

alter table if exists public.t_movie
    add constraint movie_type_id_fk
        foreign key (type_id)
            references public.t_directory_value (id);

comment on table public.t_movie is 'Фильмы пользователя из списка "Буду смотреть" на КП';
comment on column public.t_movie.rus_name is 'Русское название';
comment on column public.t_movie.eng_name is 'Английское название';
comment on column public.t_movie.year is 'Год';
comment on column public.t_movie.kp_rating is 'Рейтинг КП';
comment on column public.t_movie.imdb_rating is 'Рейтинг IMDB';
comment on column public.t_movie.duration is 'Продолжительность';
comment on column public.t_movie.kp_link is 'Ссылка на КП';
comment on column public.t_movie.poster_id is 'Постер';
comment on column public.t_movie.country_id is 'Страна';
comment on column public.t_movie.director_id is 'Режиссер';
comment on column public.t_movie.type_id is 'Тип (фильм, сериал)';
comment on column public.t_movie.deleted is 'Удален из списка';

--changeset admin:20211017-4 splitStatements:false stripComments:false
create table if not exists public.t_movie_actors (
                                       movie_id int8 not null,
                                       actor_id int8 not null,
                                       primary key (movie_id, actor_id)
);

alter table if exists public.t_movie_actors
    add constraint movie_actors_directory_value_id_fk
        foreign key (actor_id)
            references public.t_directory_value (id);

alter table if exists public.t_movie_actors
    add constraint movie_actors_movie_id_fk
        foreign key (movie_id)
            references public.t_movie (id);

comment on table public.t_movie_actors is 'Таблица для связи фильм-актеры';
comment on column public.t_movie_actors.movie_id is 'Фильм';
comment on column public.t_movie_actors.actor_id is 'Актер';

--changeset admin:20211017-5 splitStatements:false stripComments:false
create table if not exists public.t_movie_genres (
                                       movie_id int8 not null,
                                       genre_id int8 not null,
                                       primary key (movie_id, genre_id)
);

alter table if exists public.t_movie_genres
    add constraint movie_genres_directory_value_id_fk
        foreign key (genre_id)
            references public.t_directory_value (id);

alter table if exists public.t_movie_genres
    add constraint movie_genres_movie_id_fk
        foreign key (movie_id)
            references public.t_movie (id);

comment on table public.t_movie_genres is 'Таблица для связи фильм-жанры';
comment on column public.t_movie_genres.movie_id is 'Фильм';
comment on column public.t_movie_genres.genre_id is 'Жанр';

--changeset admin:20211017-6 splitStatements:false stripComments:false
insert into public.t_directory (id, display_name, name)
values (nextval('t_directory_id_seq'), 'Страна', 'COUNTRY');

insert into public.t_directory (id, display_name, name)
values (nextval('t_directory_id_seq'), 'Жанр', 'GENRE');

insert into public.t_directory (id, display_name, name)
values (nextval('t_directory_id_seq'), 'Режиссер', 'DIRECTOR');

insert into public.t_directory (id, display_name, name)
values (nextval('t_directory_id_seq'), 'Актер', 'ACTOR');

insert into public.t_directory (id, display_name, name)
values (nextval('t_directory_id_seq'), 'Тип', 'TYPE');

--changeset admin:20211017-7 splitStatements:false stripComments:false
--Действия в скрипте на heroku выполнены вручную, так как нет прав для копирования. Для выполнения на других контурах необходимо убрать preCondition
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 select count(*) from public.t_directory where name = 'COUNTRY'
BEGIN;
create temporary table temp_json (values text) on commit drop;
COPY temp_json FROM PROGRAM 'curl "https://data.gov.ru/sites/default/files/opendata/7710168515-ObscherossiyskiyklassifikatorstranmiraOXM/data-2016-09-21T00-00-00-structure-2016-09-21T00-00-00.json"';

insert into t_directory_value (id, key, value, short_value, directory)
select (nextval('t_directory_value_id_seq')) as id,
       values->>'ALFA2' as key,
       values->>'FULLNAME' as value,
       values->>'SHORTNAME' as short_value,
       (select id from t_directory where name = 'COUNTRY') as directory
from   (
           select json_array_elements(replace(values,'\','\\')::json) as values
           from   temp_json
       ) a;
COMMIT;

--changeset admin:20211017-8 splitStatements:false stripComments:false
UPDATE t_directory_value SET value = 'Корея Южная', short_value = 'КОРЕЯ ЮЖНАЯ' WHERE key = 'KR';
UPDATE t_directory_value SET value = 'Великобритания', short_value = 'ВЕЛИКОБРИТАНИЯ' WHERE key = 'GB';
UPDATE t_directory_value SET value = 'Венесуэла', short_value = 'ВЕНЕСУЭЛА' WHERE key = 'VE';
UPDATE t_directory_value SET value = 'Каймановы острова', short_value = 'КАЙМАНОВЫ ОСТРОВА' WHERE key = 'KY';
UPDATE t_directory_value SET value = 'ОАЭ', short_value = 'ОАЭ' WHERE key = 'AE';
UPDATE t_directory_value SET value = 'США', short_value = 'США' WHERE key = 'US';
UPDATE t_directory_value SET value = 'Тайвань', short_value = 'ТАЙВАНЬ' WHERE key = 'TW';
UPDATE t_directory_value SET value = 'Чехия', short_value = 'ЧЕХИЯ' WHERE key = 'CZ';
UPDATE t_directory_value SET value = 'ЮАР', short_value = 'ЮАР' WHERE key = 'ZA';

INSERT INTO t_directory_value(id, key, value, short_value, directory)
VALUES (nextval('t_directory_value_id_seq'), 'RU', 'СССР', 'СССР', (select id from t_directory where name = 'COUNTRY'));

INSERT INTO t_directory_value(id, key, value, short_value, directory)
VALUES (nextval('t_directory_value_id_seq'), 'FILM', 'Фильм', 'Фильм', (select id from t_directory where name = 'TYPE'));
INSERT INTO t_directory_value(id, key, value, short_value, directory)
VALUES (nextval('t_directory_value_id_seq'), 'SERIES', 'Сериал', 'Сериал', (select id from t_directory where name = 'TYPE'));

