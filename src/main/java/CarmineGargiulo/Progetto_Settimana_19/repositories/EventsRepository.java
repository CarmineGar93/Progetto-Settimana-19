package CarmineGargiulo.Progetto_Settimana_19.repositories;

import CarmineGargiulo.Progetto_Settimana_19.entities.Event;
import CarmineGargiulo.Progetto_Settimana_19.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventsRepository extends JpaRepository<Event, UUID> {
    List<Event> findByOrganizer(User organizer);
}
