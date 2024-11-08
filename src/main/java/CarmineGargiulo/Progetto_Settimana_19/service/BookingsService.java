package CarmineGargiulo.Progetto_Settimana_19.service;

import CarmineGargiulo.Progetto_Settimana_19.entities.Booking;
import CarmineGargiulo.Progetto_Settimana_19.exceptions.NotFoundException;
import CarmineGargiulo.Progetto_Settimana_19.repositories.BookingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingsService {
    @Autowired
    private BookingsRepository bookingsRepository;

    public Page<Booking> findAllBookings(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return bookingsRepository.findAll(pageable);
    }

    public Booking findBookingById(UUID bookingId) {
        return bookingsRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("booking", bookingId));
    }


}
