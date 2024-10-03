package Mapper;

import DTO.MovieNoSelectionDTO;
import DTO.SelectionOutDTO;
import Entity.Selection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SelectionOutDTOMapper {

    public static SelectionOutDTO map(Selection selection) {
        List<MovieNoSelectionDTO> movieNoSelectionDTOS = new ArrayList<>();
        if(selection.getMovies() != null && !selection.getMovies().isEmpty()) {
            movieNoSelectionDTOS = selection.getMovies().stream()
                    .map(MovieNoSelectionDTOMapper::map)
                    .collect(Collectors.toList());
        }
        SelectionOutDTO selectionOutDTO = new SelectionOutDTO(selection.getName(), movieNoSelectionDTOS);
        return selectionOutDTO;
    }

    public static List<SelectionOutDTO> map(List<Selection> selections) {
        return selections.stream()
                .map(SelectionOutDTOMapper::map)
                .collect(Collectors.toList());
    }
}
