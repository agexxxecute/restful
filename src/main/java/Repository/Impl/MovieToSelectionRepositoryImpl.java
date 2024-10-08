package Repository.Impl;

import DB.DBUtil;
import Repository.MovieToSelectionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieToSelectionRepositoryImpl implements MovieToSelectionRepository {

    private static MovieToSelectionRepository instance = new MovieToSelectionRepositoryImpl();

    private static String FIND_BY_MOVIE_ID = "SELECT * FROM movie_selection WHERE movie_id=?";
    private static String FIND_BY_SELECTION_ID = "SELECT * FROM movie_selection WHERE selection_id=?";
    private static String ADD_MOVIE_TO_SELECTION = "INSERT INTO movie_selection (movie_id, selection_id) VALUES (?,?)";
    private static String DELETE_BY_MOVIE_ID = "DELETE FROM movie_selection WHERE movie_id=?";
    private static String DELETE_BY_SELECTION_ID = "DELETE FROM movie_selection WHERE selection_id=?";

    public static MovieToSelectionRepository getInstance() {
        return instance;
    }

    public List<Integer[]> findByMovieId(int movieId) {
        List<Integer[]> movieToSelections = new ArrayList<>();
        try(Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_MOVIE_ID)){

            preparedStatement.setInt(1, movieId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Integer[] movieToSelection = new Integer[]{resultSet.getInt("movie_id"), resultSet.getInt("selection_id")};
                movieToSelections.add(movieToSelection);
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return movieToSelections;
    }

    public List<Integer[]> findBySelectionId(int selectionId) {
        List<Integer[]> movieToSelections = new ArrayList<>();
        try(Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_SELECTION_ID)){

            preparedStatement.setInt(1, selectionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Integer[] movieToSelection = new Integer[]{resultSet.getInt("movie_id"), resultSet.getInt("selection_id")};
                movieToSelections.add(movieToSelection);
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return movieToSelections;
    }

    public int[] addMovieToSelection(int movieId, int selectionId) {
        int[] result = new int[2];
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_MOVIE_TO_SELECTION, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setInt(1, movieId);
            preparedStatement.setInt(2, selectionId);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                result[0] = resultSet.getInt(2);
                result[1] = resultSet.getInt(3);
            }
        } catch (SQLException e){
            throw new RuntimeException();
        }
        return result;
    }

    public boolean deleteByMovieId(int movieId) {
        boolean result = false;
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_MOVIE_ID)){
            preparedStatement.setInt(1, movieId);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteBySelectionId(int selectionId) {
        boolean result = false;
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_SELECTION_ID)){
            preparedStatement.setInt(1, selectionId);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }
}
