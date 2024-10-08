package Repository;

import java.util.List;

public interface MovieToSelectionRepository {
    List<Integer[]> findByMovieId(int movieId);
    List<Integer[]> findBySelectionId(int selectionId);
    int[] addMovieToSelection(int movieId, int selectionId);
    boolean deleteByMovieId(int movieId);
    boolean deleteBySelectionId(int selectionId);
}
