package Mapper;

import DTO.SelectionNoIdDTO;
import Entity.Selection;

import java.util.List;
import java.util.stream.Collectors;

public class SelectionNoIdDTOMapper {
    public static SelectionNoIdDTO map(Selection selection) {
        SelectionNoIdDTO selectionNoIdDTO = new SelectionNoIdDTO(selection.getName());
        return selectionNoIdDTO;
    }

    public static List<SelectionNoIdDTO> map(List<Selection> selections) {
        List<SelectionNoIdDTO> selectionNoIdDTOS = selections.stream()
                .map(SelectionNoIdDTOMapper::map)
                .collect(Collectors.toList());
        return selectionNoIdDTOS;
    }
}
