package org.vinimad.app.model.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.vinimad.app.model.Film;
import org.vinimad.app.model.Film$;

import java.util.Optional;

@ApplicationScoped
public class FilmRepository {

    @Inject
    JPAStreamer jpaStreamer;

    public Optional<Film> getFilm(short filmId) {
        return jpaStreamer.stream(Film.class)
                .filter(Film$.id.in(filmId))
                .findFirst();
    }
}
