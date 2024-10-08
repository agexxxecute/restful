package Service.Impl;

import DTO.MovieInDTO;
import DTO.MovieOutDTO;
import DTO.MovieUpdateDTO;
import Entity.Movie;
import Entity.Selection;
import Mapper.MovieOutDTOMapper;
import Mapper.MovieInDTOMapper;
import Mapper.MovieUpdateDTOMapper;
import Repository.Impl.MovieRepositoryImpl;
import Repository.Impl.MovieToSelectionRepositoryImpl;
import Repository.Impl.SelectionRepositoryImpl;
import Repository.MovieToSelectionRepository;
import Repository.SelectionRepository;
import Service.MovieService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MovieServiceImpl implements MovieService {

    private static MovieServiceImpl instance;
    private MovieRepositoryImpl movieRepository = new MovieRepositoryImpl();
    private MovieInDTOMapper movieInDTOMapper = new MovieInDTOMapper();
    private MovieUpdateDTOMapper movieUpdateDTOMapper = new MovieUpdateDTOMapper();
    private MovieOutDTOMapper movieOutDTOMapper = new MovieOutDTOMapper();
    private SelectionRepository selectionRepository = new SelectionRepositoryImpl();
    private MovieToSelectionRepository movieToSelectionRepository = new MovieToSelectionRepositoryImpl();

    public static MovieServiceImpl getInstance() {
        if (instance == null) {
            instance = new MovieServiceImpl();
        }
        return instance;
    }

    public List<MovieOutDTO> findAll() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(instance::findSelections)
                .map(movieOutDTOMapper::map)
                .collect(Collectors.toList());
    }

    public MovieOutDTO findById(int id) {
        Movie movie = movieRepository.getMovieById(id);
        movie = findSelections(movie);
        if(movie != null) {
            return movieOutDTOMapper.map(movie);
        } else return null;
    }

    public List<MovieOutDTO> findAllSerials() {
        List<Movie> serials = movieRepository.getAllSerials();
        if(serials != null) {
            return movieOutDTOMapper.map(serials);
        } else return null;
    }

    public Movie add(MovieInDTO movieInDTO) {
        Movie movie = movieInDTOMapper.map(movieInDTO);
        return movieRepository.addMovie(movie);
    }

    public MovieUpdateDTO update(MovieUpdateDTO movieUpdateDTO) {
        Movie movie = movieUpdateDTOMapper.map(movieUpdateDTO);
        movieRepository.updateMovie(movie);
        return movieUpdateDTO;
    }

    public void delete(int movieId) {
        movieRepository.deleteMovie(movieId);
    }

    private Movie findSelections(Movie movie) {

        List<Integer[]> selectionsIds = movieToSelectionRepository.findByMovieId(movie.getId());
        List<Selection> selections = new ArrayList<>();
        /*List<Selection> selections = selectionsIds.stream()
                .map(selectionsIds::get.[1])
                .map(selectionRepository::findSelectionById)
                .collect(Collectors.toList());
        movie.setSelections(selections);*/
        for(Integer[] ids : selectionsIds) {
            selections.add(selectionRepository.findSelectionById(ids[1]));
        }
        movie.setSelections(selections);
        return movie;
    }
}
