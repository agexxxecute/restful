package Repository.Impl;

import DB.DBUtil;
import Entity.*;
import Repository.DirectorRepository;
import Repository.MovieRepository;
import Repository.MovieToSelectionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieRepositoryImpl implements MovieRepository {

    private static MovieRepositoryImpl instance = new MovieRepositoryImpl();
    private MovieToSelectionRepository movieToSelectionRepository = new MovieToSelectionRepositoryImpl();
    private DirectorRepository directorRepository;

    private static String FIND_MOVIE_BY_ID = "SELECT * FROM movie WHERE id = ?";
    private static String FIND_ALL_SERIALS = "SELECT * FROM movie WHERE isserial = true";
    private static String CREATE_MOVIE = "INSERT INTO movie (title, year, isserial, director_id) VALUES (?, ?, ?, ?)";
    private static String FIND_MOVIE_SELECTIONS = "SELECT * FROM movie_selection WHERE movie_id = ?";
    private static String UPDATE_MOVIE = "UPDATE movie SET title = ?, year = ?, isserial = ?, director_id = ? WHERE id = ?";
    private static String DELETE_MOVIE = "DELETE FROM movie WHERE id = ?";
    private String DELETE_DIRECTORS = "UPDATE movie SET director_id = null WHERE director_id = ?";


    public static MovieRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new MovieRepositoryImpl();
        }
        return instance;
    }

    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        try(Connection connection = DBUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM movie")){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                movies.add(createMovie(resultSet));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return movies;
    }

    public Movie getMovieById(int id) {
        Movie movie = null;
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_MOVIE_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                movie = createMovie(resultSet);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return movie;
    }

    public Movie addMovie(Movie movie) {
        Movie newMovie = null;
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(CREATE_MOVIE, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setInt(2, movie.getYear());
            preparedStatement.setBoolean(3, movie.isSerial());
            if(movie.getDirector() == null){
                preparedStatement.setNull(4, Types.NULL);
            } else{
                preparedStatement.setInt(4, movie.getDirector().getId());
            }
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()) {
                newMovie = createMovie(rs);
            }
            if(movie.getSelections() != null && !movie.getSelections().isEmpty()){
                for(int i = 0; i < movie.getSelections().size(); i++){
                    movieToSelectionRepository.addMovieToSelection(newMovie.getId(), movie.getSelections().get(i).getId());
                }
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return newMovie;
    }

    public Movie updateMovie(Movie movie) {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MOVIE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setInt(2, movie.getYear());
            preparedStatement.setBoolean(3, movie.isSerial());
            if(movie.getDirector() == null){
                preparedStatement.setNull(4, Types.NULL);
            } else{
                preparedStatement.setInt(4, movie.getDirector().getId());
            }
            preparedStatement.setInt(5, movie.getId());
            preparedStatement.executeUpdate();

            if(movie.getSelections() != null && !movie.getSelections().isEmpty()){
                movieToSelectionRepository.deleteByMovieId(movie.getId());
                for(int i = 0; i < movie.getSelections().size(); i++){
                    movieToSelectionRepository.addMovieToSelection(movie.getId(), movie.getSelections().get(i).getId());
                }
            }

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()) {
                movie = createMovie(rs);
            }

        } catch (SQLException e){
            throw new RuntimeException();
        }

        return movie;
    }

    public boolean deleteMovie(int movieId) {
        boolean result = false;
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MOVIE)){
            preparedStatement.setInt(1, movieId);
            movieToSelectionRepository.deleteByMovieId(movieId);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return result;
    }

    public void removeDirectors(int directorId){
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DIRECTORS)){
            preparedStatement.setInt(1, directorId);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    public List<Movie> getAllSerials(){
        List<Movie> serials = new ArrayList<>();
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SERIALS)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                serials.add(createMovie(resultSet));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return serials;

    }

    private Movie createMovie(ResultSet resultSet) throws SQLException {
        directorRepository = DirectorRepositoryImpl.getInstance();
        Director director = directorRepository.findById(resultSet.getInt("director_id"));
        Movie movie = new Movie(resultSet.getInt("id"), resultSet.getString("title"),resultSet.getInt("year"), resultSet.getBoolean("isserial"), director, null);
        return movie;
    }
}
