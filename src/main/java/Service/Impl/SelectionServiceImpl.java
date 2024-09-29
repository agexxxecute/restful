package Service.Impl;

import DTO.SelectionInDTO;
import DTO.SelectionOutDTO;
import DTO.SelectionUpdateDTO;
import Entity.Movie;
import Entity.MovieToSelection;
import Entity.Selection;
import Mapper.SelectionInDTOMapper;
import Mapper.SelectionOutDTOMapper;
import Mapper.SelectionUpdateDTOMapper;
import Repository.MovieRepository;
import Repository.Impl.MovieRepositoryImpl;
import Repository.MovieToSelectionRepository;
import Repository.Impl.SelectionRepositoryImpl;
import Repository.SelectionRepository;
import Service.SelectionService;

import java.util.List;
import java.util.stream.Collectors;

public class SelectionServiceImpl implements SelectionService {

    private MovieRepositoryImpl movieRepository = MovieRepositoryImpl.getInstance();
    private SelectionRepositoryImpl selectionRepository = new SelectionRepositoryImpl();
    private SelectionUpdateDTOMapper selectionUpdateDTOMapper = new SelectionUpdateDTOMapper();
    private SelectionInDTOMapper selectionInDTOMapper = new SelectionInDTOMapper();
    private static SelectionServiceImpl instance;


    public static SelectionServiceImpl getInstance() {
        if (instance == null) {
            instance = new SelectionServiceImpl();
        }
        return instance;
    }

    public List<SelectionOutDTO> findAll (){
        List<Selection> selections = selectionRepository.findAll();
        return selections.stream()
                .map(this::findMovies)
                .map(SelectionOutDTOMapper::map)
                .collect(Collectors.toList());
    }

    public SelectionOutDTO findById (int id){
        Selection selection = selectionRepository.findSelectionById(id);
        selection = findMovies(selection);
        return SelectionOutDTOMapper.map(selection);
    }

    public SelectionInDTO add(SelectionInDTO selectionInDTO){
        Selection selection = selectionInDTOMapper.map(selectionInDTO);
        selection = selectionRepository.addSelection(selection);
        return selectionInDTOMapper.map(selection);
    }

    public SelectionUpdateDTO update(SelectionUpdateDTO selectionUpdateDTO){
        Selection selection = selectionUpdateDTOMapper.map(selectionUpdateDTO);
        return SelectionUpdateDTOMapper.map(selectionRepository.updateSelection(selection));
    }

    public boolean delete(int selectionId){
        return selectionRepository.deleteSelection(selectionId);
    }

    private  Selection findMovies(Selection selection){
        List<MovieToSelection> movieIds = MovieToSelectionRepository.findBySelectionId(selection.getId());
        List<Movie> movies = movieIds.stream()
                .map(MovieToSelection::getMovieId)
                .map(movieRepository::getMovieById)
                .collect(Collectors.toList());
        selection.setMovies(movies);
        return selection;
    }
}
