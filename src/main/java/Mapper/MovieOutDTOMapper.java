package Mapper;

import DTO.MovieOutDTO;
import DTO.SelectionNoIdDTO;
import Entity.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieOutDTOMapper {
    public static MovieOutDTO map(Movie movie) {
        List<SelectionNoIdDTO> selectionNoIdDTOS = new ArrayList<>();
        if(!movie.getSelections().isEmpty()) {
            selectionNoIdDTOS = movie.getSelections().stream()
                    .map(SelectionNoIdDTOMapper::map)
                    .collect(Collectors.toList());
        }
        return new MovieOutDTO(null, movie.getTitle(), movie.getYear(), movie.isSerial(), movie.getDirector(), selectionNoIdDTOS);
    }

    public static List<MovieOutDTO> map(List<Movie> movies) {
        return movies.stream()
                .map(MovieOutDTOMapper::map)
                .collect(Collectors.toList());
    }
}
