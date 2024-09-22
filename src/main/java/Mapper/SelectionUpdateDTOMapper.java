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
}
