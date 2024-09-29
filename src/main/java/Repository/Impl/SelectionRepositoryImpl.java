package Repository.Impl;

import DB.DBUtil;
import DTO.MovieToSelectionNoIDDTO;
import Entity.MovieToSelection;
import Entity.Selection;
import Mapper.MovieToSelectionNoIDDTOMapper;
import Repository.MovieToSelectionRepository;
import Repository.SelectionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SelectionRepositoryImpl implements SelectionRepository {

    private static SelectionRepository instance;

    private static String FIND_BY_ID = "SELECT * FROM selection WHERE id = ?";
    private static String ADD_SELECTION = "INSERT INTO selection (name) VALUES (?)";
    private static String FIND_ALL = "SELECT * FROM selection";
    private static String UPDATE_SELECTION = "UPDATE selection SET name = ? WHERE id = ?";
    private static String DELETE_SELECTION = "DELETE FROM selection WHERE id = ?";



    public static SelectionRepository getInstance() {
        if (instance == null) {
            instance = new SelectionRepositoryImpl();
        }
        return instance;
    }

    public Selection findSelectionById(int id) {
        Selection selection = null;
        try(Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                selection = createSelection(resultSet);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return selection;
    }

    public Selection addSelection(Selection selection) {
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_SELECTION, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, selection.getName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            Selection newSelection = new Selection();
            if(resultSet.next()){
                newSelection = createSelection(resultSet);
            }
            if(selection.getMovies() != null && !selection.getMovies().isEmpty()){
                for(int i = 0; i < selection.getMovies().size(); i++){
                    MovieToSelectionNoIDDTO movieToSelectionNoIDDTO = new MovieToSelectionNoIDDTO(selection.getMovies().get(i).getId(), newSelection.getId());
                    MovieToSelection movieToSelection = MovieToSelectionNoIDDTOMapper.map(movieToSelectionNoIDDTO);
                    MovieToSelectionRepository.addMovieToSelection(movieToSelection);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return selection;
    }

    public Selection updateSelection(Selection selection) {
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SELECTION, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, selection.getName());
            preparedStatement.setInt(2, selection.getId());
            preparedStatement.executeUpdate();

            if(selection.getMovies() != null && !selection.getMovies().isEmpty()){
                MovieToSelectionRepository.deleteBySelectionId(selection.getId());
                for(int i = 0; i < selection.getMovies().size(); i++){
                    MovieToSelectionNoIDDTO movieToSelectionNoIDDTO = new MovieToSelectionNoIDDTO(selection.getMovies().get(i).getId(), selection.getId());
                    MovieToSelection movieToSelection = MovieToSelectionNoIDDTOMapper.map(movieToSelectionNoIDDTO);
                    MovieToSelectionRepository.addMovieToSelection(movieToSelection);
                }
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                selection = createSelection(resultSet);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return selection;
    }

    public boolean deleteSelection(int selectionId) {
        boolean result = false;
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SELECTION)){
            preparedStatement.setInt(1, selectionId);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public List<Selection> findAll(){
        List<Selection> selections = new ArrayList<>();
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                selections.add(createSelection(resultSet));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return selections;
    }

    private Selection createSelection(ResultSet resultSet) throws SQLException {

        Selection selection = new Selection(resultSet.getInt("id"), resultSet.getString("name"), null);
        return selection;
    }
}
