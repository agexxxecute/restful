package DTO;

import java.util.List;

public class DirectorOutDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private List<String> movies;

    public DirectorOutDTO(Integer id, String firstName, String lastName, List<String> movies) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.movies = movies;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<String> getMovies() {
        return movies;
    }
}
