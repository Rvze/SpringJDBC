select count(*)
from books_libraries
         join libraries l on l.libraryid = books_libraries.libraryid
where libraryname = 'РНБ'