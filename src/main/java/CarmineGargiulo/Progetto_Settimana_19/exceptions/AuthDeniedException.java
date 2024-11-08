package CarmineGargiulo.Progetto_Settimana_19.exceptions;

public class AuthDeniedException extends RuntimeException {
    public AuthDeniedException(String message) {
        super(message);
    }
}
