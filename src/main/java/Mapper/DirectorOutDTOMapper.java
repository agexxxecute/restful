package Mapper;

import DTO.DirectorOutDTO;
import Entity.Director;

import java.util.List;
import java.util.stream.Collectors;

public class DirectorOutDTOMapper {
    public static DirectorOutDTO map(Director director) {
        return new DirectorOutDTO(director.getId(), director.getFirstName(), director.getLastName());
    }

    public static Director map (DirectorOutDTO directorOutDTO) {
        return new Director(directorOutDTO.getId(), directorOutDTO.getFirstName(), directorOutDTO.getLastName());
    }

    public static List<DirectorOutDTO> map(List<Director> directors) {
        return directors.stream()
                .map(DirectorOutDTOMapper::map)
                .collect(Collectors.toList());
    }
}
