package Mapper;

import DTO.DirectorOutDTO;
import DTO.MovieInDTO;
import Entity.Director;
import Entity.Movie;
import Entity.Selection;
import Service.DirectorService;

import java.util.List;

public class MovieInDTOMapper {
    public static Movie map (MovieInDTO movieInDTO) {
        Movie movie = new Movie();
        movie.setTitle(movieInDTO.getTitle());
        movie.setYear(movieInDTO.getYear());
        movie.setSerial(movieInDTO.isSerial());
        Integer director_id = movieInDTO.getDirector_id();
        if(director_id != null && director_id > 0) {
            DirectorOutDTO directorOutDTO = DirectorService.findById(director_id);
            Director director = DirectorOutDTOMapper.map(directorOutDTO);
            movie.setDirector(director);
        }

        if(movieInDTO.getSelections() != null && !movieInDTO.getSelections().isEmpty()) {
            movie.setSelections(movieInDTO.getSelections());
        }

        return movie;
    }

    public static MovieInDTO map (Movie movie) {
        Integer director_id = null;
        if(movie.getDirector() != null) {
            director_id = movie.getDirector().getId();
        }

        List<Selection> selections = null;
        if(movie.getSelections() != null && !movie.getSelections().isEmpty()) {
            selections = movie.getSelections();
        }

        MovieInDTO movieInDTO = new MovieInDTO(movie.getTitle(), movie.getYear(), movie.isSerial(), director_id, selections);
        return movieInDTO;
    }
}
