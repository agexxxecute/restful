package Mapper;

import DTO.SelectionUpdateDTO;
import Entity.Selection;

public class SelectionUpdateDTOMapper {
    public static Selection map (SelectionUpdateDTO selectionUpdateDTO) {
        Selection selection = new Selection();
        selection.setId(selectionUpdateDTO.getId());
        selection.setName(selectionUpdateDTO.getName());
        if(selectionUpdateDTO.getMovies() != null && !selectionUpdateDTO.getMovies().isEmpty()) {
            selection.setMovies(selectionUpdateDTO.getMovies());
        }
        return selection;
    }

    public static SelectionUpdateDTO map (Selection selection) {
        SelectionUpdateDTO selectionUpdateDTO = new SelectionUpdateDTO(selection.getId(),selection.getName(),selection.getMovies());
        return selectionUpdateDTO;
    }
}
