package Service;

import DTO.DirectorInDTO;
import DTO.DirectorOutDTO;
import DTO.DirectorUpdateDTO;
import Entity.Director;

import java.util.List;

public interface DirectorService {
    List<DirectorOutDTO> findAll();

    Director add(DirectorInDTO directorInDTO);

    DirectorUpdateDTO update(DirectorUpdateDTO directorUpdateDTO);

    Integer delete(int directorId);

    DirectorOutDTO findById(int id);
}
