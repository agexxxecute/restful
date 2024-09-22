package DTO;

import Entity.Movie;

import java.util.List;

public class SelectionInDTO {
    private String name;
    private List<Movie> movies;

    public SelectionInDTO() {
    }

    public SelectionInDTO(String name, List<Movie> movies) {
        this.name = name;
        this.movies = movies;
    }

    public String getName() {
        return name;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
