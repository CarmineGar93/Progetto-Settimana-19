package CarmineGargiulo.Progetto_Settimana_19.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "booking_id")
    private UUID bookingId;
    @Column(name = "booked_seats")
    private int bookedSeats;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public Booking(int bookedSeats, User user, Event event) {
        this.bookedSeats = bookedSeats;
        this.user = user;
        this.event = event;
    }

    @Override
    public String toString() {
        return "Booking " + bookingId +
                " = (" + user +
                "), (" + event +
                "), bookedSeats: " + bookedSeats;
    }
}
