package Mapper;

import DTO.DirectorInDTO;
import Entity.Director;
import Entity.Movie;
import Repository.Impl.MovieRepositoryImpl;
import Repository.MovieRepository;

import java.util.List;
import java.util.stream.Collectors;

public class DirectorInDTOMapper {
    private MovieRepository movieRepository = new MovieRepositoryImpl();

    public Director map (DirectorInDTO directorInDTO) {
        List<Movie> movies = null;
        /*if(directorInDTO.getMovies() != null && !directorInDTO.getMovies().isEmpty()) {
            movies = directorInDTO.getMovies().stream()
                    .map(movieRepository::getMovieById)
                    .collect(Collectors.toList());
        }*/
        return new Director(null, directorInDTO.getFirstName(), directorInDTO.getLastName(), movies);
    }

    public static DirectorInDTO map (Director director) {
        return new DirectorInDTO(director.getFirstName(), director.getLastName());
    }
}
