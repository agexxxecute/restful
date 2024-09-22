package Service;

import DTO.MovieInDTO;
import DTO.MovieOutDTO;
import DTO.MovieUpdateDTO;
import Entity.Movie;
import Entity.MovieToSelection;
import Entity.Selection;
import Mapper.MovieOutDTOMapper;
import Mapper.MovieInDTOMapper;
import Mapper.MovieUpdateDTOMapper;
import Repository.MovieRepository;
import Repository.MovieToSelectionRepository;
import Repository.SelectionRepository;

import java.util.List;
import java.util.stream.Collectors;

public class MovieService {

    private static MovieService instance;

    public static MovieService getInstance() {
        if (instance == null) {
            instance = new MovieService();
        }
        return instance;
    }

    public static List<MovieOutDTO> findAll() {
        List<Movie> movies = MovieRepository.getAllMovies();
        return movies.stream()
                .map(MovieService::findSelections)
                .map(MovieOutDTOMapper::map)
                .collect(Collectors.toList());
    }

    public static MovieOutDTO findById(int id) {
        Movie movie = MovieRepository.getMovieById(id);
        movie = findSelections(movie);
        if(movie != null) {
            return MovieOutDTOMapper.map(movie);
        } else return null;
    }

    public static List<MovieOutDTO> findAllSerials() {
        List<Movie> serials = MovieRepository.getAllSerials();
        if(serials != null) {
            return MovieOutDTOMapper.map(serials);
        } else return null;
    }

    public static MovieInDTO add(MovieInDTO movieInDTO) {
        Movie movie = MovieInDTOMapper.map(movieInDTO);
        MovieRepository.addMovie(movie);
        return MovieInDTOMapper.map(movie);
    }

    public static MovieUpdateDTO update(MovieUpdateDTO movieUpdateDTO) {
        Movie movie = MovieUpdateDTOMapper.map(movieUpdateDTO);
        MovieRepository.updateMovie(movie);
        return movieUpdateDTO;
    }

    public static MovieUpdateDTO delete(MovieUpdateDTO movieUpdateDTO) {
        Movie movie = MovieUpdateDTOMapper.map(movieUpdateDTO);
        MovieRepository.deleteMovie(movie);
        return movieUpdateDTO;
    }

    private static Movie findSelections(Movie movie) {
        List<MovieToSelection> selectionsIds = MovieToSelectionRepository.findByMovieId(movie.getId());
        List<Selection> selections = selectionsIds.stream()
                .map(MovieToSelection::getSelectionId)
                .map(SelectionRepository::findSelectionById)
                .collect(Collectors.toList());
        movie.setSelections(selections);
        return movie;
    }
}
