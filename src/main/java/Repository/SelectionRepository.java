package Repository;

import Entity.Selection;

import java.util.List;

public interface SelectionRepository {
    Selection findSelectionById(int id);

    void addSelection(Selection selection);

    void updateSelection(Selection selection);

    boolean deleteSelection(int selectionId);

    public List<Selection> findAll();
}
