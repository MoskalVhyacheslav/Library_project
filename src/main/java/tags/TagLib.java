package tags;


import entity.Book;

import java.util.List;

public class TagLib {

    public static boolean contains(List<Book> userBooks, Book book){
        return userBooks.contains(book);
    }

}
