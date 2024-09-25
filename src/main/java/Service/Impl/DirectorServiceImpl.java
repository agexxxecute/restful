package Service.Impl;

import DTO.DirectorInDTO;
import DTO.DirectorOutDTO;
import Entity.Director;
import Mapper.DirectorInDTOMapper;
import Mapper.DirectorOutDTOMapper;
import Repository.Impl.DirectorRepositoryImpl;
import Service.DirectorService;

import java.util.List;

public class DirectorServiceImpl implements DirectorService {

    private DirectorRepositoryImpl directorRepository = new DirectorRepositoryImpl();
    private DirectorOutDTOMapper directorOutDTOMapper = new DirectorOutDTOMapper();
    private static DirectorServiceImpl instance;


    public static DirectorServiceImpl getInstance() {
        if (instance == null) {
            instance = new DirectorServiceImpl();
        }
        return instance;
    }

    public List<DirectorOutDTO> findAll(){
        List<Director> directors = directorRepository.findAll();
        return directorOutDTOMapper.map(directors);
    }

    public DirectorOutDTO add(DirectorInDTO directorInDTO){
        Director director = DirectorInDTOMapper.map(directorInDTO);
        director = directorRepository.addDirector(director);
        DirectorOutDTO directorOutDTO = DirectorOutDTOMapper.map(director);
        return directorOutDTO;

    }

    public DirectorOutDTO update(DirectorOutDTO directorOutDTO){
        Director director = DirectorOutDTOMapper.map(directorOutDTO);
        directorRepository.updateDirector(director);
        return DirectorOutDTOMapper.map(director);
    }

    public Integer delete(int directorId){
        directorRepository.deleteDirectorById(directorId);
        return directorId;
    }

    public DirectorOutDTO findById(int id){
        return DirectorOutDTOMapper.map(directorRepository.findById(id));
    }
}
