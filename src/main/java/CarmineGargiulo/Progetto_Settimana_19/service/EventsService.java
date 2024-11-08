package CarmineGargiulo.Progetto_Settimana_19.service;

import CarmineGargiulo.Progetto_Settimana_19.dto.EventDTO;
import CarmineGargiulo.Progetto_Settimana_19.entities.Event;
import CarmineGargiulo.Progetto_Settimana_19.entities.User;
import CarmineGargiulo.Progetto_Settimana_19.exceptions.BadRequestException;
import CarmineGargiulo.Progetto_Settimana_19.exceptions.NotFoundException;
import CarmineGargiulo.Progetto_Settimana_19.exceptions.UnauthorizedException;
import CarmineGargiulo.Progetto_Settimana_19.repositories.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;


@Service
public class EventsService {
    @Autowired
    private EventsRepository eventsRepository;
    @Autowired
    private List<DateTimeFormatter> formatters;

    public Page<Event> findAllEvent(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return eventsRepository.findAll(pageable);
    }

    public Event getEventById(UUID eventId) {
        return eventsRepository.findById(eventId).orElseThrow(() -> new NotFoundException("event", eventId));
    }

    public Event saveEvent(EventDTO body, User organizer) {
        LocalDate date = validateDate(body.date());
        return eventsRepository.save(new Event(body.title(), body.description(), body.location(),
                date, body.maxSeats(), organizer));
    }

    public List<Event> findAllEventByOrganizer(User organizer) {
        return eventsRepository.findByOrganizer(organizer);
    }

    private LocalDate validateDate(String date) {
        for (DateTimeFormatter formatter : formatters) {
            try {
                LocalDate dateFormatted = LocalDate.parse(date, formatter);
                if (dateFormatted.isBefore(LocalDate.now()))
                    throw new BadRequestException("Date must be in the future");
                return dateFormatted;
            } catch (DateTimeParseException ignored) {

            }
        }
        throw new BadRequestException("Format date not supported");
    }

    public Event findEventByIdAndUpdate(EventDTO body, UUID eventId, User organizer) {
        Event searched = getEventById(eventId);
        if (!searched.getOrganizer().getUserId().equals(organizer.getUserId()))
            throw new UnauthorizedException("You don't have access to modify this event");
        searched.setDate(validateDate(body.date()));
        searched.setDescription(body.description());
        searched.setTitle(body.title());
        searched.setLocation(body.location());
        searched.setMaxSeats(body.maxSeats());
        return eventsRepository.save(searched);
    }

    public void deleteEvent(UUID eventId, User organizer) {
        Event searched = getEventById(eventId);
        if (!searched.getOrganizer().getUserId().equals(organizer.getUserId()))
            throw new UnauthorizedException("You don't have access to delete this event");
        eventsRepository.delete(searched);
    }

}
