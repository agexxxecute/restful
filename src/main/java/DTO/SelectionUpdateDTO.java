package DTO;

import Entity.Movie;

import java.util.List;

public class SelectionUpdateDTO {
    private Integer id;
    private String name;
    private List<Integer> movies;

    public SelectionUpdateDTO() {
    }

    public SelectionUpdateDTO(Integer id, String name, List<Integer> movies) {
        this.id = id;
        this.name = name;
        this.movies = movies;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getMovies() {
        return movies;
    }
}
