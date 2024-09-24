package Mapper;

import DTO.DirectorOutDTO;
import DTO.MovieUpdateDTO;
import Entity.Director;
import Entity.Movie;
import Service.DirectorService;
import Service.Impl.DirectorServiceImpl;

public class MovieUpdateDTOMapper {

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
            Director director = DirectorOutDTOMapper.map(directorOutDTO);
            movie.setDirector(director);
        }

        if(movieUpdateDTO.getSelections() != null){
            movie.setSelections(movieUpdateDTO.getSelections());
        }
        return movie;
    }
}
