select libraries.libraryid, libraryname
from libraries
         join books_libraries bl on libraries.libraryid = bl.libraryid
         join books b on b.bookid = bl.bookid
where bookname = 'Евгений Онегин'