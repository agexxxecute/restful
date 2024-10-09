package Mapper;

import DTO.DirectorUpdateDTO;
import Entity.Director;
import Entity.Movie;
import Repository.Impl.MovieRepositoryImpl;
import Repository.MovieRepository;

import java.util.List;
import java.util.stream.Collectors;

public class DirectorUpdateDTOMapper {

    private MovieRepository movieRepository = new MovieRepositoryImpl();

    public Director map (DirectorUpdateDTO directorUpdateDTO){
        Director director = new Director();
        director.setId(directorUpdateDTO.getId());
        director.setFirstName(directorUpdateDTO.getFirstName());
        director.setLastName(directorUpdateDTO.getLastName());
        return director;
    }
}
