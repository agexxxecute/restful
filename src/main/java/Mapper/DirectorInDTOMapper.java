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
        return new Director(null, directorInDTO.getFirstName(), directorInDTO.getLastName(), movies);
    }

}
