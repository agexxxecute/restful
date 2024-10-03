package Mapper;

import DTO.DirectorOutDTO;
import DTO.MovieUpdateDTO;
import Entity.Director;
import Entity.Movie;
import Entity.Selection;
import Repository.Impl.SelectionRepositoryImpl;
import Repository.SelectionRepository;
import Service.DirectorService;
import Service.Impl.DirectorServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieUpdateDTOMapper {
    private SelectionRepository selectionRepository = new SelectionRepositoryImpl();
    private DirectorOutDTOMapper directorOutDTOMapper = new DirectorOutDTOMapper();

    private DirectorService directorService = new DirectorServiceImpl();
    public Movie map (MovieUpdateDTO movieUpdateDTO) {
        Movie movie = new Movie();
        movie.setId(movieUpdateDTO.getId());
        movie.setTitle(movieUpdateDTO.getTitle());
        movie.setYear(movieUpdateDTO.getYear());
        movie.setSerial(movieUpdateDTO.isSerial());
        Integer director_id = movieUpdateDTO.getDirector_id();
        if(director_id != null && director_id > 0) {
            DirectorOutDTO directorOutDTO = directorService.findById(director_id);
            Director director = directorOutDTOMapper.map(directorOutDTO);
            movie.setDirector(director);
        }

        if(movieUpdateDTO.getSelections() != null){
            List<Selection> selections = movieUpdateDTO.getSelections().stream()
                            .map(selectionRepository::findSelectionById)
                                    .collect(Collectors.toList());
            movie.setSelections(selections);
        }
        return movie;
    }
}
