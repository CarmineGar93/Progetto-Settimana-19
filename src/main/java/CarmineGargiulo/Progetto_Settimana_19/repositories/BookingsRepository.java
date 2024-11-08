package CarmineGargiulo.Progetto_Settimana_19.repositories;

import CarmineGargiulo.Progetto_Settimana_19.entities.Booking;
import CarmineGargiulo.Progetto_Settimana_19.entities.Event;
import CarmineGargiulo.Progetto_Settimana_19.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingsRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByUser(User user);

    @Query("SELECT COUNT(b.bookedSeats) FROM Booking b WHERE b.event = :event")
    int checkSeatsOccupiedByEvent(Event event);
}
