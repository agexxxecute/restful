package Service;

import DTO.DirectorInDTO;
import DTO.DirectorOutDTO;
import Entity.Director;
import Mapper.DirectorInDTOMapper;
import Mapper.DirectorOutDTOMapper;
import Repository.DirectorRepository;

import java.util.List;

public class DirectorService {
    public static List<DirectorOutDTO> findAll(){
        List<Director> directors = DirectorRepository.findAllDirectors();
        return DirectorOutDTOMapper.map(directors);
    }

    public static DirectorInDTO add(DirectorInDTO directorInDTO){
        Director director = DirectorInDTOMapper.map(directorInDTO);
        DirectorRepository.addDirector(director);
        return DirectorInDTOMapper.map(director);
    }

    public static DirectorOutDTO update(DirectorOutDTO directorOutDTO){
        Director director = DirectorOutDTOMapper.map(directorOutDTO);
        DirectorRepository.updateDirector(director);
        return DirectorOutDTOMapper.map(director);
    }

    public static DirectorOutDTO delete(DirectorOutDTO directorOutDTO){
        Director director = DirectorOutDTOMapper.map(directorOutDTO);
        DirectorRepository.deleteDirectorById(director.getId());
        return DirectorOutDTOMapper.map(director);
    }

    public static DirectorOutDTO findById(int id){
        return DirectorOutDTOMapper.map(DirectorRepository.findById(id));
    }
}
