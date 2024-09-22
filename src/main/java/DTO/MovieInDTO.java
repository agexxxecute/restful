package DTO;

import Entity.Director;
import Entity.Selection;

import java.util.List;

public class MovieInDTO {
    private String title;
    private int year;
    private boolean isSerial;
    private Integer director_id;
    private List<Selection> selections;

    public MovieInDTO() {
    }

    public MovieInDTO(String title, int year, boolean isSerial, Integer director_id, List<Selection> selections) {
        this.title = title;
        this.year = year;
        this.isSerial = isSerial;
        this.director_id = director_id;
        this.selections = selections;
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

    public Integer getDirector_id() {
        return director_id;
    }

    public List<Selection> getSelections() {
        return selections;
    }
}
