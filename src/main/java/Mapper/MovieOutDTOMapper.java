package Mapper;

import DTO.MovieOutDTO;
import DTO.SelectionNoIdDTO;
import Entity.Movie;
import Entity.Selection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieOutDTOMapper {

    public MovieOutDTO map(Movie movie) {
        List<SelectionNoIdDTO> selectionNoIdDTOS = new ArrayList<>();
        if(!movie.getSelections().isEmpty()) {
            selectionNoIdDTOS = movie.getSelections().stream()
                    .map(SelectionNoIdDTOMapper::map)
                    .collect(Collectors.toList());
        }
        return new MovieOutDTO(null, movie.getTitle(), movie.getYear(), movie.isSerial(), movie.getDirector(), selectionNoIdDTOS);
    }

    public List<MovieOutDTO> map(List<Movie> movies) {
        return movies.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

//    public Movie map(MovieOutDTO movieOutDTO) {
//        Movie movie = new Movie();
//        List<Selection> selections = movieOutDTO.getSelections().stream()
//                        .map(SelectionNoIdDTOMapper::map)
//        movie.setTitle(movieOutDTO.getTitle());
//        movie.setYear(movieOutDTO.getYear());
//        movie.setSerial(movieOutDTO.isSerial());
//        movie.getSelections()
//    }

//    public List<Movie> map(List <MovieOutDTO> movieOutDTOS) {
//        return movieOutDTOS.stream()
//                .map(this::map)
//                .collect(Collectors.toList());
//    }
}
