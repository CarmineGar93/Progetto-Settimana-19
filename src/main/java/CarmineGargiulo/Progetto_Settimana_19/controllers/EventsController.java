package CarmineGargiulo.Progetto_Settimana_19.controllers;

import CarmineGargiulo.Progetto_Settimana_19.dto.EventDTO;
import CarmineGargiulo.Progetto_Settimana_19.entities.Event;
import CarmineGargiulo.Progetto_Settimana_19.entities.User;
import CarmineGargiulo.Progetto_Settimana_19.exceptions.BadRequestException;
import CarmineGargiulo.Progetto_Settimana_19.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventsController {
    @Autowired
    private EventsService eventsService;

    @GetMapping
    public Page<Event> getAllEvents(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "date") String sortBy) {
        return eventsService.findAllEvent(page, size, sortBy);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Event saveEvent(@AuthenticationPrincipal User organizer, @RequestBody @Validated EventDTO body,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
            throw new BadRequestException(message);
        }
        return eventsService.saveEvent(body, organizer);
    }

    @GetMapping("/myevents")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public List<Event> getAllOrganizerEvents(@AuthenticationPrincipal User organizer) {
        return eventsService.findAllEventByOrganizer(organizer);
    }

    @PutMapping("/myevents/{eventId}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public Event modifyEvent(@AuthenticationPrincipal User organizer, @RequestBody @Validated EventDTO body,
                             BindingResult bindingResult, @PathVariable UUID eventId) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
            throw new BadRequestException(message);
        }
        return eventsService.findEventByIdAndUpdate(body, eventId, organizer);
    }

    @DeleteMapping("/myevents/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public void deleteEvent(@AuthenticationPrincipal User organizer, @PathVariable UUID eventId) {
        eventsService.deleteEvent(eventId, organizer);
    }


}
