package Service;

import DTO.DirectorInDTO;
import DTO.DirectorOutDTO;

import java.util.List;

public interface DirectorService {
    List<DirectorOutDTO> findAll();

    DirectorInDTO add(DirectorInDTO directorInDTO);

    DirectorOutDTO update(DirectorOutDTO directorOutDTO);

    boolean delete(int directorId);

    DirectorOutDTO findById(int id);
}
