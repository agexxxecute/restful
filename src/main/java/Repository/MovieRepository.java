package Repository;

import Entity.Movie;

import java.util.List;

public interface MovieRepository {

    List<Movie> findAll();

    Movie getMovieById(int id);

    Movie addMovie(Movie movie);

    Movie updateMovie(Movie movie);

    boolean deleteMovie(int movieId);

    void removeDirectors(int movieId);

    List<Movie> getAllSerials();

    List<Movie> findByDirectorId(int directorId);

    Movie updateDirector(int movieId, int directorId);
}
