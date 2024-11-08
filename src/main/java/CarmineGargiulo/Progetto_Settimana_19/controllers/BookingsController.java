package CarmineGargiulo.Progetto_Settimana_19.controllers;

import CarmineGargiulo.Progetto_Settimana_19.dto.BookingDTO;
import CarmineGargiulo.Progetto_Settimana_19.entities.Booking;
import CarmineGargiulo.Progetto_Settimana_19.entities.User;
import CarmineGargiulo.Progetto_Settimana_19.exceptions.BadRequestException;
import CarmineGargiulo.Progetto_Settimana_19.service.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
public class BookingsController {
    @Autowired
    private BookingsService bookingsService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Booking> getAllBooking(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "event.date") String sortBy) {
        return bookingsService.findAllBookings(page, size, sortBy);
    }

    @GetMapping("/{bookingId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Booking getSingleBooking(@PathVariable UUID bookingId) {
        return bookingsService.findBookingById(bookingId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking saveBooking(@AuthenticationPrincipal User current, @RequestBody @Validated BookingDTO body,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
            throw new BadRequestException(message);
        }
        return bookingsService.saveBooking(body, current);

    }
}
