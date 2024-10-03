package Mapper;

import DTO.SelectionUpdateDTO;
import Entity.Movie;
import Entity.Selection;
import Repository.Impl.MovieRepositoryImpl;
import Repository.Impl.SelectionRepositoryImpl;
import Repository.MovieRepository;
import Repository.SelectionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SelectionUpdateDTOMapper {

    private MovieRepository movieRepository = new MovieRepositoryImpl();

    public Selection map (SelectionUpdateDTO selectionUpdateDTO) {
        Selection selection = new Selection();
        selection.setId(selectionUpdateDTO.getId());
        selection.setName(selectionUpdateDTO.getName());
        List<Movie> movies = null;
        if(selectionUpdateDTO.getMovies() != null && !selectionUpdateDTO.getMovies().isEmpty()) {
            movies = selectionUpdateDTO.getMovies().stream()
                            .map(movieRepository::getMovieById)
                                    .collect(Collectors.toList());
        }
        selection.setMovies(movies);
        return selection;
    }

    public static SelectionUpdateDTO map (Selection selection) {
        List<Integer> movies = null;
        if(selection.getMovies() != null && !selection.getMovies().isEmpty()) {
            movies = selection.getMovies().stream()
                    .map(Movie::getId)
                    .collect(Collectors.toList());
        }
        SelectionUpdateDTO selectionUpdateDTO = new SelectionUpdateDTO(selection.getId(),selection.getName(),movies);
        return selectionUpdateDTO;
    }
}
