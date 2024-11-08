package CarmineGargiulo.Progetto_Settimana_19.repositories;

import CarmineGargiulo.Progetto_Settimana_19.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingsRepository extends JpaRepository<Booking, UUID> {
}
