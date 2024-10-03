package Repository.Impl;

import DB.DBUtil;
import Entity.MovieToSelection;
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

    public MovieToSelection addMovieToSelection(MovieToSelection movieToSelection) {
        MovieToSelection newMovieToSelection = new MovieToSelection();
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_MOVIE_TO_SELECTION, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setInt(1, movieToSelection.getMovieId());
            preparedStatement.setInt(2, movieToSelection.getSelectionId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                newMovieToSelection.setId(resultSet.getInt(1));
                newMovieToSelection.setMovieId(resultSet.getInt(2));
                newMovieToSelection.setSelectionId(resultSet.getInt(3));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return movieToSelection;
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
