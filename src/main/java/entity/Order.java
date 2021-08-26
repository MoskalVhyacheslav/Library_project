package entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Order implements Serializable {
    private User user;
    private Book book;
    private LocalDate creditDate;
    private double credit;

    public Order(){
    }

    public Order(User user,Book book,LocalDate creditDate){
        this.user = user;
        this.book = book;
        this.creditDate = creditDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getCreditDate() {
        return creditDate;
    }

    public void setCreditDate(LocalDate creditDate) {
        this.creditDate = creditDate;
    }

    public double getCredit() {
        return credit ;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Order{" +
                "user=" + user +
                ", book=" + book +
                ", creditDate=" + creditDate +
                ", credit=" + credit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (Double.compare(order.credit, credit) != 0) return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;
        if (book != null ? !book.equals(order.book) : order.book != null) return false;
        return creditDate != null ? creditDate.equals(order.creditDate) : order.creditDate == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = user != null ? user.hashCode() : 0;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (creditDate != null ? creditDate.hashCode() : 0);
        temp = Double.doubleToLongBits(credit);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
