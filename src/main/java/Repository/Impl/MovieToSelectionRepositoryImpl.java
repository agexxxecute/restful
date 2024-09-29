package Repository.Impl;

import DB.DBUtil;
import Entity.MovieToSelection;
import Repository.MovieToSelectionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieToSelectionRepositoryImpl implements MovieToSelectionRepository {

    private static String FIND_BY_MOVIE_ID = "SELECT * FROM movie_selection WHERE movie_id=?";
    private static String FIND_BY_SELECTION_ID = "SELECT * FROM movie_selection WHERE selection_id=?";
    private static String ADD_MOVIE_TO_SELECTION = "INSERT INTO movie_selection (movie_id, selection_id) VALUES (?,?)";
    private static String DELETE_BY_MOVIE_ID = "DELETE FROM movie_selection WHERE movie_id=?";
   private static String DELETE_BY_SELECTION_ID = "DELETE FROM movie_selection WHERE selection_id=?";

    public List<MovieToSelection> findByMovieId(int movieId) {
        List<MovieToSelection> movieToSelections = new ArrayList<>();
        try(Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_MOVIE_ID)){

            preparedStatement.setInt(1, movieId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                MovieToSelection movieToSelection = new MovieToSelection();
                movieToSelection.setMovieId(resultSet.getInt("movie_id"));
                movieToSelection.setSelectionId(resultSet.getInt("selection_id"));
                movieToSelections.add(movieToSelection);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return movieToSelections;
    }

    public List<MovieToSelection> findBySelectionId(int selectionId) {
        List<MovieToSelection> movieToSelections = new ArrayList<>();
        try(Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_SELECTION_ID)){

            preparedStatement.setInt(1, selectionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                MovieToSelection movieToSelection = new MovieToSelection();
                movieToSelection.setMovieId(resultSet.getInt("movie_id"));
                movieToSelection.setSelectionId(resultSet.getInt("selection_id"));
                movieToSelections.add(movieToSelection);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return movieToSelections;
    }

    public void addMovieToSelection(MovieToSelection movieToSelection) {
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_MOVIE_TO_SELECTION)){
            preparedStatement.setInt(1, movieToSelection.getMovieId());
            preparedStatement.setInt(2, movieToSelection.getSelectionId());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteByMovieId(int movieId) {
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_MOVIE_ID)){
            preparedStatement.setInt(1, movieId);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void deleteBySelectionId(int selectionId) {
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_SELECTION_ID)){
            preparedStatement.setInt(1, selectionId);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
