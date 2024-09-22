package DTO;

import Entity.Director;

public class MovieNoSelectionDTO {
    private String title;
    private int year;
    private boolean isSerial;
    private Director director;

    public MovieNoSelectionDTO() {
    }

    public MovieNoSelectionDTO(String title, int year, boolean isSerial, Director director) {
        this.title = title;
        this.year = year;
        this.isSerial = isSerial;
        this.director = director;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public boolean isSerial() {
        return isSerial;
    }

    public Director getDirector() {
        return director;
    }
}
