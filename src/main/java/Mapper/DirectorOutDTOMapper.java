package Mapper;

import DTO.DirectorOutDTO;
import Entity.Director;

import java.util.List;
import java.util.stream.Collectors;

public class DirectorOutDTOMapper {
    public DirectorOutDTO map(Director director) {
        return new DirectorOutDTO(director.getId(), director.getFirstName(), director.getLastName());
    }

    public Director map (DirectorOutDTO directorOutDTO) {
        return new Director(directorOutDTO.getId(), directorOutDTO.getFirstName(), directorOutDTO.getLastName());
    }

    public List<DirectorOutDTO> map(List<Director> directors) {
        return directors.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
