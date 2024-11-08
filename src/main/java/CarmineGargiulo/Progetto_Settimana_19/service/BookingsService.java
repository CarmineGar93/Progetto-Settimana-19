package CarmineGargiulo.Progetto_Settimana_19.service;

import CarmineGargiulo.Progetto_Settimana_19.dto.BookingDTO;
import CarmineGargiulo.Progetto_Settimana_19.entities.Booking;
import CarmineGargiulo.Progetto_Settimana_19.entities.Event;
import CarmineGargiulo.Progetto_Settimana_19.entities.User;
import CarmineGargiulo.Progetto_Settimana_19.exceptions.AuthDeniedException;
import CarmineGargiulo.Progetto_Settimana_19.exceptions.BadRequestException;
import CarmineGargiulo.Progetto_Settimana_19.exceptions.NotFoundException;
import CarmineGargiulo.Progetto_Settimana_19.exceptions.UnauthorizedException;
import CarmineGargiulo.Progetto_Settimana_19.repositories.BookingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookingsService {
    @Autowired
    private BookingsRepository bookingsRepository;
    @Autowired
    private EventsService eventsService;

    public Page<Booking> findAllBookings(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return bookingsRepository.findAll(pageable);
    }

    public Booking findBookingById(UUID bookingId) {
        return bookingsRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("booking", bookingId));
    }

    public Booking saveBooking(BookingDTO body, User currentUser) {
        Event event = eventsService.getEventById(body.eventId());
        int occupiedSeats = bookingsRepository.checkSeatsOccupiedByEvent(event);
        if (event.getMaxSeats() - occupiedSeats < body.bookedSeats())
            throw new BadRequestException("Not enough available seats for this event");
        if (event.getOrganizer().getUserId().equals(currentUser.getUserId()))
            throw new UnauthorizedException("Organizer cannot make a booking for his/her own event");
        return bookingsRepository.save(new Booking(body.bookedSeats(), currentUser, event));
    }

    public List<Booking> findBookingsByUser(User user) {
        return bookingsRepository.findByUser(user);
    }

    public void deleteBookingById(UUID bookingId, User user) {
        Booking booking = findBookingById(bookingId);
        if (!booking.getUser().getUserId().equals(user.getUserId()))
            throw new AuthDeniedException("You don't have access to delete this booking");
        bookingsRepository.delete(booking);
    }
}
