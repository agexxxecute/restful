package Repository;

import Entity.Movie;

import java.util.List;

public interface MovieRepository {

    List<Movie> getAllMovies();

    Movie getMovieById(int id);

    void addMovie(Movie movie);

    void updateMovie(Movie movie);

    void deleteMovie(int movieId);

    List<Movie> getAllSerials();
}
