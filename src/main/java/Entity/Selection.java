package Entity;

import java.util.List;

public class Selection {
    private Integer id;
    private String name;
    private List<Movie> movies;

    public Selection() {
    }

    public Selection(Integer id, String name, List<Movie> movies) {
        this.id = id;
        this.name = name;
        this.movies = movies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
