package DTO;

import Entity.Director;
import Entity.Selection;

import java.util.List;

public class MovieUpdateDTO {
    private Integer id;
    private String title;
    private int year;
    private boolean isSerial;
    private Integer director_id;
    private List<Integer> selections;

    public MovieUpdateDTO() {
    }

    public MovieUpdateDTO(Integer id, String title, int year, boolean isSerial, Integer director_id, List<Integer> selections) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.isSerial = isSerial;
        this.director_id = director_id;
        this.selections = selections;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public Integer getDirector_id() {
        return director_id;
    }

    public List<Integer> getSelections() {
        return selections;
    }

    public boolean isSerial() {
        return isSerial;
    }
}
