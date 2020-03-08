package file.univ_playground.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long userId){
        super("Could not find user " + userId);
    }
}
