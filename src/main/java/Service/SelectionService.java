package Service;

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
import Repository.SelectionRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SelectionService {

    private static MovieRepository movieRepository = new MovieRepositoryImpl();

    public static List<SelectionOutDTO> findAll (){
        List<Selection> selections = SelectionRepository.findAll();
        return selections.stream()
                .map(SelectionService::findMovies)
                .map(SelectionOutDTOMapper::map)
                .collect(Collectors.toList());
    }

    public static SelectionOutDTO findById (int id){
        Selection selection = SelectionRepository.findSelectionById(id);
        selection = findMovies(selection);
        return SelectionOutDTOMapper.map(selection);
    }

    public static SelectionInDTO add(SelectionInDTO selectionInDTO){
        Selection selection = SelectionInDTOMapper.map(selectionInDTO);
        SelectionRepository.addSelection(selection);
        return SelectionInDTOMapper.map(selection);
    }

    public static SelectionUpdateDTO update(SelectionUpdateDTO selectionUpdateDTO){
        Selection selection = SelectionUpdateDTOMapper.map(selectionUpdateDTO);
        SelectionRepository.updateSelection(selection);
        return selectionUpdateDTO;
    }

    public static SelectionUpdateDTO delete(SelectionUpdateDTO selectionUpdateDTO){
        Selection selection = SelectionUpdateDTOMapper.map(selectionUpdateDTO);
        SelectionRepository.deleteSelection(selection);
        return selectionUpdateDTO;
    }

    private static Selection findMovies(Selection selection){
        List<MovieToSelection> movieIds = MovieToSelectionRepository.findBySelectionId(selection.getId());
        List<Movie> movies = movieIds.stream()
                .map(MovieToSelection::getMovieId)
                .map(movieRepository::getMovieById)
                .collect(Collectors.toList());
        selection.setMovies(movies);
        return selection;
    }
}
