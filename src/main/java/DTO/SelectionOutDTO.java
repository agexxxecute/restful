package DTO;

import Entity.Movie;

import java.util.List;

public class SelectionOutDTO {
    private String name;
    private List<MovieNoSelectionDTO> movies;

    public SelectionOutDTO() {
    }

    public SelectionOutDTO(String name, List<MovieNoSelectionDTO> movies) {
        this.name = name;
        this.movies = movies;
    }

    public String getName() {
        return name;
    }

    public List<MovieNoSelectionDTO> getMovies() {
        return movies;
    }
}
