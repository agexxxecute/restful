package Entity;

public class MovieToSelection {
    private Integer id;
    private int movieId;
    private int selectionId;

    public MovieToSelection() {
    }

    public MovieToSelection(Integer id, int movieId, int selectionId) {
        this.id = id;
        this.movieId = movieId;
        this.selectionId = selectionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(int selectionId) {
        this.selectionId = selectionId;
    }
}
