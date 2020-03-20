package file.univ_playground.exception;

public class PasswordWrongException extends RuntimeException {
    public PasswordWrongException(){
        super("Password is wrong");
    }
}
