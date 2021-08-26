package tags;

import entity.Book;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class BookTag extends TagSupport {
    private Book book;
    private List<Book> userBooks;

    public void setBook(Book book){
        this.book = book;
    }

    public void setUserBooks(List<Book> userBooks){
        this.userBooks=userBooks;
    }

    @Override
    public int doStartTag() throws JspException {
        StringBuilder result = new StringBuilder();
        result.append("<td>").append(book.getTitle()).append("</td>")
                .append("<td>").append(book.getAuthor()).append("</td>")
                .append("<td>").append(book.getEdition()).append("</td>")
                .append("<td>").append(book.getEditionDate()).append("</td>");

        if (checkBook(book)) {
            result.append("<th><a href=\"controller?command=deleteOrder\">Delete book</a><th>");
        } else {
            result.append("<th><a href=\"controller?command=addOrder\">" +
                    "<c:out value="+"/>"+
                    "Add book</a><th>");
        }

        try{
            pageContext.getOut().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;

    }

    private boolean checkBook(Book book){
        return userBooks.contains(book);
    }
}
