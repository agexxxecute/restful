package Mapper;

import DTO.SelectionInDTO;
import Entity.Movie;
import Entity.Selection;

import java.util.List;

public class SelectionInDTOMapper {
    public static Selection map (SelectionInDTO selectionInDTO){
        Selection selection = new Selection();
        selection.setName(selectionInDTO.getName());

        if(selectionInDTO.getMovies() != null && !selectionInDTO.getMovies().isEmpty()) {
            selection.setMovies(selectionInDTO.getMovies());
        }
        return selection;
    }

    public static SelectionInDTO map (Selection selection){
        List<Movie> movies = null;
        if(selection.getMovies() != null && !selection.getMovies().isEmpty()) {
            movies = selection.getMovies();
        }
        SelectionInDTO selectionInDTO = new SelectionInDTO(selection.getName(),movies);
        return selectionInDTO;
    }
}
