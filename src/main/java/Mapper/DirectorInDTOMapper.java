package Mapper;

import DTO.DirectorInDTO;
import Entity.Director;

public class DirectorInDTOMapper {
    public static Director map (DirectorInDTO directorInDTO) {
        return new Director(null, directorInDTO.getFirstName(), directorInDTO.getLastName());
    }

    public static DirectorInDTO map (Director director) {
        return new DirectorInDTO(director.getFirstName(), director.getLastName());
    }
}
