package CarmineGargiulo.Progetto_Settimana_19.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String resource, UUID id) {
        super(resource + " with id " + id + " does not exists");
    }
}
