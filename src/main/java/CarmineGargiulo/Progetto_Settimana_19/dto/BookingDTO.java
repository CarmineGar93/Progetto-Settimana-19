package CarmineGargiulo.Progetto_Settimana_19.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BookingDTO(
        @NotNull(message = "Event id must be provided")
        UUID eventId,
        @NotEmpty(message = "Booked seats number must be provided")
        @Min(value = 1, message = "You need to book at least for 1 person")
        @Max(value = 6, message = "Max 6 seats allowed per user")
        int bookedSeats) {
}
