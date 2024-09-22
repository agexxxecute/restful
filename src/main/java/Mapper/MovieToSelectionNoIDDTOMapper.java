package Mapper;

import DTO.MovieToSelectionNoIDDTO;
import Entity.MovieToSelection;

public class MovieToSelectionNoIDDTOMapper {
        public static MovieToSelection map (MovieToSelectionNoIDDTO movieToSelectionNoIDDTO) {
            MovieToSelection movieToSelection = new MovieToSelection();
            movieToSelection.setMovieId(movieToSelectionNoIDDTO.getMovieId());
            movieToSelection.setSelectionId(movieToSelectionNoIDDTO.getSelectionId());
            return movieToSelection;
        }

        public static MovieToSelectionNoIDDTO map (MovieToSelection movieToSelection) {
            MovieToSelectionNoIDDTO movieToSelectionNoIDDTO = new MovieToSelectionNoIDDTO(movieToSelection.getMovieId(), movieToSelection.getSelectionId());
            return movieToSelectionNoIDDTO;
        }
}
