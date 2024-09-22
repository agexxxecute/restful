package DTO;

public class MovieToSelectionNoIDDTO {
    private Integer movieId;
    private Integer selectionId;

    public MovieToSelectionNoIDDTO() {
    }

    public MovieToSelectionNoIDDTO(Integer movieId, Integer selectionId) {
        this.movieId = movieId;
        this.selectionId = selectionId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public Integer getSelectionId() {
        return selectionId;
    }
}
