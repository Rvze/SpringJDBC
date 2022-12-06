truncate authors, genres, books, books_authors cascade;

insert into authors(authorname, rating, yearofbirth, biography)
values ('Пушкин',
        10,
        1799,
        'Алекса́ндр Серге́евич Пу́шкин — русский поэт, драматург и прозаик, заложивший основы русского реалистического направления, литературный критик и теоретик литературы, историк, публицист, журналист. Один из самых авторитетных литературных деятелей первой трети XIX века.');

insert into authors(authorname, rating, yearofbirth, biography)
values ('nurgun',
        10,
        2002,
        'cool');

insert into genres(genrename, description, agelimit)
values ('Roman',
        ' это литературный жанр, чаще прозаический, зародившийся в средние века у романских народов как рассказ на народном языке и ныне превратившийся в самый распространенный вид эпической литературы, изображающий жизнь персонажа с её волнующими страстями, борьбой, социальными противоречиями и стремлениями к идеалу',
        16);

insert into books(bookname, year, genreid, title)
values ('Евгений Онегин',
        1831,
        1,
        'title');

-- BOOKS_AUTHORS
insert into books_authors(authorid, bookid)
values (1,
        1);

insert into books_authors(authorid, bookid)
values (2,
        1);

insert into libraries(libraryname)
values ('РНБ');

insert into books_libraries(libraryid, bookid)
values (1,
        1);