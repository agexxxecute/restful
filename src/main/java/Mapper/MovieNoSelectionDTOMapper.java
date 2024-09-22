package Mapper;

import DTO.MovieNoSelectionDTO;
import DTO.MovieOutDTO;
import Entity.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class MovieNoSelectionDTOMapper {

    public static MovieNoSelectionDTO map (Movie movie){
        return new MovieNoSelectionDTO(movie.getTitle(), movie.getYear(), movie.isSerial(), movie.getDirector());
    }

    public static List<MovieNoSelectionDTO> map (List<Movie> movies){
        return movies.stream()
                .map(MovieNoSelectionDTOMapper::map)
                .collect(Collectors.toList());
    }
}
