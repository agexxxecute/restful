package Repository;

import Entity.Movie;

import java.util.List;

public interface MovieRepository {

    List<Movie> findAll();

    Movie getMovieById(int id);

    Movie addMovie(Movie movie);

    Movie updateMovie(Movie movie);

    void deleteMovie(int movieId);

    List<Movie> getAllSerials();
}
