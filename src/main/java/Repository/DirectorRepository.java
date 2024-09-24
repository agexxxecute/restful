package Repository;

import Entity.Director;

import java.util.List;

public interface DirectorRepository {

    List<Director> findAllDirectors();

    Director findById(int id);

    Director addDirector(Director director);

    Director updateDirector(Director director);

    boolean deleteDirectorById(int id);

}
