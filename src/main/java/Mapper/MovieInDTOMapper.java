package Mapper;

import DTO.DirectorOutDTO;
import DTO.MovieInDTO;
import Entity.Director;
import Entity.Movie;
import Entity.Selection;
import Repository.Impl.MovieRepositoryImpl;
import Repository.Impl.SelectionRepositoryImpl;
import Repository.MovieRepository;
import Repository.SelectionRepository;
import Service.DirectorService;
import Service.Impl.DirectorServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

public class MovieInDTOMapper {
    private DirectorService directorService = new DirectorServiceImpl();
    private SelectionRepository selectionRepository = new SelectionRepositoryImpl();
    private DirectorOutDTOMapper directorOutDTOMapper = new DirectorOutDTOMapper();

    public Movie map (MovieInDTO movieInDTO) {
        Movie movie = new Movie();
        movie.setTitle(movieInDTO.getTitle());
        movie.setYear(movieInDTO.getYear());
        movie.setSerial(movieInDTO.isSerial());
        Integer director_id = movieInDTO.getDirector_id();
        if(director_id != null && director_id > 0) {
            DirectorOutDTO directorOutDTO = directorService.findById(director_id);
            Director director = new Director(directorOutDTO.getId(), directorOutDTO.getFirstName(), directorOutDTO.getLastName());
            movie.setDirector(director);
        }

        if(movieInDTO.getSelections() != null && !movieInDTO.getSelections().isEmpty()) {
            List<Selection> selections = movieInDTO.getSelections().stream()
                            .map(selectionRepository::findSelectionById)
                                    .collect(Collectors.toList());
            movie.setSelections(selections);
        }

        return movie;
    }
}
