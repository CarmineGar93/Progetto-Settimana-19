package CarmineGargiulo.Progetto_Settimana_19.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
