package Entity;

import java.util.List;

public class Movie {
    private Integer id;
    private String title;
    private int year;
    private boolean isSerial;
    private Director director;
    private List<Selection> selections;

    public Movie() {
    }

    public Movie(int id, String title, int year, boolean isSerial, Director director, List<Selection> selections) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.isSerial = isSerial;
        this.director = director;
        this.selections = selections;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isSerial() {
        return isSerial;
    }

    public void setSerial(boolean serial) {
        isSerial = serial;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public List<Selection> getSelections() {
        return selections;
    }

    public void setSelections(List<Selection> selections) {
        this.selections = selections;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", isSerial=" + isSerial +
                '}';
    }
}
