package exceptions;

public class DAOException extends Exception {

    public DAOException(){
        super();
    }

    public DAOException(String message,Throwable exception){
        super(message,exception);
    }

    public DAOException(Throwable exception){
        super(exception);
    }

}
