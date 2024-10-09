package Mapper;

import DTO.DirectorOutDTO;
import DTO.MovieOutDTO;
import Entity.Director;
import Entity.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class DirectorOutDTOMapper {

    private MovieOutDTOMapper movieOutDTOMapper = new MovieOutDTOMapper();

    public DirectorOutDTO map(Director director) {
        List<String> movies = null;
        if(director.getMovies() != null && !director.getMovies().isEmpty()) {
            movies = director.getMovies().stream()
                    .map(m -> m.getTitle())
                    .collect(Collectors.toList());
        }
        return new DirectorOutDTO(director.getId(), director.getFirstName(), director.getLastName(), movies);
    }
}
