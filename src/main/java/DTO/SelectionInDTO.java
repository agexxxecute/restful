package DTO;

import Entity.Movie;

import java.util.List;

public class SelectionInDTO {
    private String name;
    private List<Integer> movies;

    public SelectionInDTO() {
    }

    public SelectionInDTO(String name, List<Integer> movies) {
        this.name = name;
        this.movies = movies;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getMovies() {
        return movies;
    }
}
