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
    private static DirectorServiceImpl instance;

    public static DirectorServiceImpl getInstance() {
        if (instance == null) {
            instance = new DirectorServiceImpl();
        }
        return instance;
    }

    public List<DirectorOutDTO> findAll(){
        List<Director> directors = directorRepository.findAllDirectors();
        return DirectorOutDTOMapper.map(directors);
    }

    public DirectorInDTO add(DirectorInDTO directorInDTO){
        Director director = DirectorInDTOMapper.map(directorInDTO);
        directorRepository.addDirector(director);
        return DirectorInDTOMapper.map(director);
    }

    public DirectorOutDTO update(DirectorOutDTO directorOutDTO){
        Director director = DirectorOutDTOMapper.map(directorOutDTO);
        directorRepository.updateDirector(director);
        return DirectorOutDTOMapper.map(director);
    }

    public boolean delete(int directorId){
        return directorRepository.deleteDirectorById(directorId);
    }

    public DirectorOutDTO findById(int id){
        return DirectorOutDTOMapper.map(directorRepository.findById(id));
    }
}
