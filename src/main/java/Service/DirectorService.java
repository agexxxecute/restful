package Service;

import DTO.DirectorInDTO;
import DTO.DirectorOutDTO;

import java.util.List;

public interface DirectorService {
    List<DirectorOutDTO> findAll();

    DirectorOutDTO add(DirectorInDTO directorInDTO);

    DirectorOutDTO update(DirectorOutDTO directorOutDTO);

    Integer delete(int directorId);

    DirectorOutDTO findById(int id);
}
