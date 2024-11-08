package CarmineGargiulo.Progetto_Settimana_19.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "event_id")
    private UUID eventId;
    @Column(nullable = false)
    private String title, description, location;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false, name = "max_seats")
    private int maxSeats;
    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;
    @OneToMany(mappedBy = "event")
    @Setter(AccessLevel.NONE)
    @JsonIgnore
    private List<Booking> bookings;

    public Event(String title, String description, String location, LocalDate date, int maxSeats, User organizer) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.maxSeats = maxSeats;
        this.organizer = organizer;
    }

    @Override
    public String toString() {
        return "Event = id: " + eventId +
                ", title: " + title +
                ", description: " + description +
                ", location: " + location +
                ", date: " + date +
                ", maxSeats: " + maxSeats +
                ", orginizer: (" + organizer + ")";
    }
}
