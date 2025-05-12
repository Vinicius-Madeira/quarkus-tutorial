package org.vinimad.app;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.vinimad.app.model.Film;
import org.vinimad.app.model.repository.FilmRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/")
public class FilmResource {

    @Inject
    FilmRepository filmRepository;

    @GET
    @Path("/helloWorld")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello World!";
    }

    @GET
    @Path("/film/{filmId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getFilm(short filmId) {
        Optional<Film> film = filmRepository.getFilm(filmId);
        return film.isPresent() ? film.get().getTitle() : "No film was found!";
    }

    @GET
    @Path("/pagedFilms/{page}/{minLength}")
    @Produces(MediaType.TEXT_PLAIN)
    public String paged(long page, short minLength) {
        return filmRepository.paged(page, minLength)
                .map(film -> String.format("%s (%d min)", film.getTitle(), film.getLength()))
                .collect(Collectors.joining("\n"));
    }

    @GET
    @Path("/pagedActors/{startsWith}/{minLength}")
    @Produces(MediaType.TEXT_PLAIN)
    public String paged(String startsWith, short minLength) {
        return filmRepository.actors(startsWith, minLength)
                .map(film -> String.format("%s (%d min): %s",
                        film.getTitle(),
                        film.getLength(),
                        film.getActors().stream().map(actor -> String.format("%s %s", actor.getFirstName(), actor.getLastName()))
                                .collect(Collectors.joining(", "))))
                                .collect(Collectors.joining("\n"));
    }

    @GET
    @Path("/update/{minLength}/{rentalRate}")
    @Produces(MediaType.TEXT_PLAIN)
    public String update(short minLength, BigDecimal rentalRate) {
        filmRepository.updateRentalRate(minLength, rentalRate);
        return filmRepository.getFilms(minLength).map(film -> String.format("%s (%d min) - %f", film.getTitle(), film.getLength(), film.getRentalRate()))
                .collect(Collectors.joining("\n"));
    }

}
