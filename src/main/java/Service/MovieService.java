package Service;

import DTO.MovieInDTO;
import DTO.MovieOutDTO;
import DTO.MovieUpdateDTO;
import Entity.Movie;

import java.util.List;

public interface MovieService{
    List<MovieOutDTO> findAll();

    MovieOutDTO findById(int id);

    List<MovieOutDTO> findAllSerials();

    Movie add(MovieInDTO movieInDTO);

    MovieUpdateDTO update(MovieUpdateDTO movieUpdateDTO);

    void delete(int movieId);
}
