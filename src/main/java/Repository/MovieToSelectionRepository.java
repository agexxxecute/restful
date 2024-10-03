package Repository;

import Entity.MovieToSelection;

import java.util.List;

public interface MovieToSelectionRepository {
    List<MovieToSelection> findByMovieId(int movieId);
    List<MovieToSelection> findBySelectionId(int selectionId);
    MovieToSelection addMovieToSelection(MovieToSelection movieToSelection);
    boolean deleteByMovieId(int movieId);
    boolean deleteBySelectionId(int selectionId);
}
