package Service.Impl;

import DTO.DirectorInDTO;
import DTO.DirectorOutDTO;
import DTO.DirectorUpdateDTO;
import Entity.Director;
import Entity.Movie;
import Mapper.DirectorInDTOMapper;
import Mapper.DirectorOutDTOMapper;
import Mapper.DirectorUpdateDTOMapper;
import Repository.Impl.DirectorRepositoryImpl;
import Repository.Impl.MovieRepositoryImpl;
import Repository.MovieRepository;
import Service.DirectorService;

import java.util.List;
import java.util.stream.Collectors;

public class DirectorServiceImpl implements DirectorService {

    private DirectorRepositoryImpl directorRepository = new DirectorRepositoryImpl();
    private DirectorOutDTOMapper directorOutDTOMapper = new DirectorOutDTOMapper();
    private DirectorUpdateDTOMapper directorUpdateDTOMapper = new DirectorUpdateDTOMapper();
    private MovieRepository movieRepository = new MovieRepositoryImpl();
    private DirectorInDTOMapper directorInDTOMapper = new DirectorInDTOMapper();
    private static DirectorServiceImpl instance;


    public static DirectorServiceImpl getInstance() {
        if (instance == null) {
            instance = new DirectorServiceImpl();
        }
        return instance;
    }

    public List<DirectorOutDTO> findAll(){
        List<Director> directors = directorRepository.findAll();
        return directors.stream()
                .map(this::findMovies)
                .map(directorOutDTOMapper::map)
                .collect(Collectors.toList());
    }

    public Director add(DirectorInDTO directorInDTO){
        Director director = directorInDTOMapper.map(directorInDTO);
        director = directorRepository.addDirector(director);
        if(directorInDTO.getMovies() != null && !directorInDTO.getMovies().isEmpty()){
            for(Integer movieId : directorInDTO.getMovies()){
                movieRepository.updateDirector(movieId, director.getId());
            }
        }
        return director;
    }

    public DirectorUpdateDTO update(DirectorUpdateDTO directorUpdateDTO){
        Director director = directorUpdateDTOMapper.map(directorUpdateDTO);
        director = directorRepository.updateDirector(director);
        if(directorUpdateDTO.getMovies() != null && !directorUpdateDTO.getMovies().isEmpty()){
            for(Integer movieId : directorUpdateDTO.getMovies()){
                movieRepository.updateDirector(movieId, director.getId());
            }
        }
        return directorUpdateDTO;
    }

    public Integer delete(int directorId){
        directorRepository.deleteDirectorById(directorId);
        return directorId;
    }

    public DirectorOutDTO findById(int id){
        Director director = directorRepository.findById(id);
        director = findMovies(director);
        return directorOutDTOMapper.map(director);
    }

    private Director findMovies(Director director){
        List<Movie> movies = movieRepository.findByDirectorId(director.getId());
        director.setMovies(movies);
        return director;
    }
}
