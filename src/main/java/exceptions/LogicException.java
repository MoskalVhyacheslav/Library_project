package exceptions;

public class LogicException extends Exception {

    public LogicException(){
        super();
    }

    public LogicException(String message,Throwable exception){
        super(message,exception);
    }

    public LogicException(Throwable exception){
        super(exception);
    }

    public LogicException(String message){
        super(message);
    }

}
