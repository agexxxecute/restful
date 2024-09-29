package Repository;

import Entity.MovieToSelection;

import java.util.List;

public interface MovieToSelectionRepository {
    List<MovieToSelection> findByMovieId(int movieId);
    List<MovieToSelection> findBySelectionId(int selectionId);
    void addMovieToSelection(MovieToSelection movieToSelection);
    void deleteByMovieId(int movieId);
    void deleteBySelectionId(int selectionId);
}
