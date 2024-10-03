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
        return directorOutDTOMapper.map(director);

    }

    public DirectorOutDTO update(DirectorOutDTO directorOutDTO){
        Director director = directorOutDTOMapper.map(directorOutDTO);
        directorRepository.updateDirector(director);
        return directorOutDTOMapper.map(director);
    }

    public Integer delete(int directorId){
        directorRepository.deleteDirectorById(directorId);
        return directorId;
    }

    public DirectorOutDTO findById(int id){
        return directorOutDTOMapper.map(directorRepository.findById(id));
    }
}
