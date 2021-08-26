package entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 */
public class Book implements Serializable{
    private String title;
    private String author;
    private String edition;
    private LocalDate editionDate;
    private int creditDays;
    private int amount;

    public Book(){
    }

    public Book(String title, String author, String edition, LocalDate editionDate,
                int creditDay, int amount) {
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.editionDate = editionDate;
        this.creditDays = creditDay;
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public LocalDate getEditionDate() {
        return editionDate;
    }

    public void setEditionDate(LocalDate editionDate) {
        this.editionDate = editionDate;
    }

    public int getCreditDays() {
        return creditDays;
    }

    public void setCreditDays(int creditDays) {
        this.creditDays = creditDays;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", edition='" + edition + '\'' +
                ", editionDate=" + editionDate +
                ", creditDay=" + creditDays +
                ", amount=" + amount +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (creditDays != book.creditDays) return false;
        if (amount != book.amount) return false;
        if (title != null ? !title.equals(book.title) : book.title != null) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (edition != null ? !edition.equals(book.edition) : book.edition != null) return false;
        return editionDate != null ? editionDate.equals(book.editionDate) : book.editionDate == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (edition != null ? edition.hashCode() : 0);
        result = 31 * result + (editionDate != null ? editionDate.hashCode() : 0);
        result = 31 * result + creditDays;
        result = 31 * result + amount;
        return result;
    }
}
