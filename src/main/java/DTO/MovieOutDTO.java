package DTO;

import Entity.Director;

import java.util.List;

public class MovieOutDTO {
    private Integer id;
    private String title;
    private int year;
    private boolean isSerial;
    private Director director;
    private List<SelectionNoIdDTO> selections;

    public MovieOutDTO(Integer id, String title, int year, boolean isSerial, Director director, List<SelectionNoIdDTO> selections) {
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

    public List<SelectionNoIdDTO> getSelections() {
        return selections;
    }
}
