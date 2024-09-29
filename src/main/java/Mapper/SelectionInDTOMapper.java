package Mapper;

import DTO.SelectionInDTO;
import Entity.Movie;
import Entity.Selection;
import Repository.Impl.MovieRepositoryImpl;
import Repository.MovieRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SelectionInDTOMapper {

    private MovieRepository movieRepository = new MovieRepositoryImpl();

    public Selection map (SelectionInDTO selectionInDTO){
        Selection selection = new Selection();
        selection.setName(selectionInDTO.getName());

        if(selectionInDTO.getMovies() != null && !selectionInDTO.getMovies().isEmpty()) {
            List<Movie> movies = selectionInDTO.getMovies().stream()
                            .map(movieRepository::getMovieById)
                                    .collect(Collectors.toList());
            selection.setMovies(movies);
        }
        return selection;
    }

    public  SelectionInDTO map (Selection selection){
        List<Integer> movies = null;
        if(selection.getMovies() != null && !selection.getMovies().isEmpty()) {
            movies = selection.getMovies().stream()
                    .map(Movie::getId)
                    .collect(Collectors.toList());
        }
        SelectionInDTO selectionInDTO = new SelectionInDTO(selection.getName(),movies);
        return selectionInDTO;
    }
}
