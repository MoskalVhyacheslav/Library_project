package entity;

import java.io.Serializable;

public class User implements Serializable{
    private String login;
    private String mail;
    private String password;
    private int creditBooks;
    private double balance;
    private double credit;
    private boolean blocked;
    private int roleId;

    public User(){
        blocked=false;
        balance=0;
        credit=0;
        roleId=3;
    }

    public User(String login,String mail,String password){
        this.login = login;
        this.mail = mail;
        this.password= password;
        this.balance = 0;
        credit=0;
        blocked = false;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public double getCredit() {
        return this.credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }


    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", mail='" + mail + '\'' +
                ", balance=" + balance +
                ", credit=" + credit +
                ", blocked=" + blocked +
                ", roleId=" + roleId +
                ", creditBooks=" + creditBooks +
                '}';
    }

    public int getCreditBooks() {
        return creditBooks;
    }

    public void setCreditBooks(int creditBooks) {
        this.creditBooks = creditBooks;
    }
}
