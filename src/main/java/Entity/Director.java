package Entity;

import java.util.List;

public class Director {
    private Integer Id;
    private String firstName;
    private String lastName;
    private List<Movie> movies;

    public Director() {
    }

    public Director(Integer id, String firstName, String lastName) {
        this.Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        movies = null;
    }

    public Director(Integer id, String firstName, String lastName, List<Movie> movies) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.movies = movies;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
