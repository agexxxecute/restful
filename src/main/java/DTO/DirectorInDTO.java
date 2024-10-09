package DTO;

import java.util.List;

public class DirectorInDTO {
    private String firstName;
    private String lastName;
    private List<Integer> movies;

    public DirectorInDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public DirectorInDTO(String firstName, String lastName, List<Integer> movies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.movies = movies;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Integer> getMovies() {
        return movies;
    }
}
