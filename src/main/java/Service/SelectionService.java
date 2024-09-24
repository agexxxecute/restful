package Service;

import DTO.SelectionInDTO;
import DTO.SelectionOutDTO;
import DTO.SelectionUpdateDTO;

import java.util.List;

public interface SelectionService {

    List<SelectionOutDTO> findAll ();

    public SelectionOutDTO findById (int id);

    public SelectionInDTO add(SelectionInDTO selectionInDTO);

    public SelectionUpdateDTO update(SelectionUpdateDTO selectionUpdateDTO);

    public boolean delete(int selectionId);
}
