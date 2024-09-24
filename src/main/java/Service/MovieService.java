package Service;

import DTO.MovieInDTO;
import DTO.MovieOutDTO;

import java.util.List;

public interface MovieService{
    List<MovieOutDTO> findAll();

    MovieOutDTO findById(int id);

    List<MovieOutDTO> findAllSerials();

    MovieInDTO add(MovieInDTO movieInDTO);

    void delete(int movieId);
}
