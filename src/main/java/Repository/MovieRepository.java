package Repository;

import DB.DBUtil;
import DTO.MovieToSelectionNoIDDTO;
import Entity.*;
import Mapper.MovieToSelectionNoIDDTOMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieRepository {
    //SELECT movie.title, movie.year FROM movie INNER JOIN movie_selection ON movie.id = movie_selection.movie_id INNER JOIN selection ON movie_selection.selection_id = selection.id
    private static String FIND_MOVIE_BY_ID = "SELECT * FROM movie WHERE id = ?";
    private static String FIND_ALL_SERIALS = "SELECT * FROM movie WHERE isserial = true";
    private static String CREATE_MOVIE = "INSERT INTO movie (title, year, isserial, director_id) VALUES (?, ?, ?, ?)";
    private static String FIND_MOVIE_SELECTIONS = "SELECT * FROM movie_selection WHERE movie_id = ?";
    private static String UPDATE_MOVIE = "UPDATE movie SET title = ?, year = ?, isserial = ?, director_id = ? WHERE id = ?";
    private static String DELETE_MOVIE = "DELETE FROM movie WHERE id = ?";

    public static List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<Movie>();
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

    public static Movie getMovieById(int id) {
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

    public static void addMovie(Movie movie) {
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(CREATE_MOVIE)){
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setInt(2, movie.getYear());
            preparedStatement.setBoolean(3, movie.isSerial());
            if(movie.getDirector() == null){
                preparedStatement.setNull(4, Types.NULL);
            } else{
                preparedStatement.setInt(4, movie.getDirector().getId());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateMovie(Movie movie) {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MOVIE)) {
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
                MovieToSelectionRepository.deleteByMovieId(movie.getId());
                for(int i = 0; i < movie.getSelections().size(); i++){
                    MovieToSelectionNoIDDTO movieToSelectionNoIDDTO = new MovieToSelectionNoIDDTO(movie.getId(), movie.getSelections().get(i).getId());
                    MovieToSelection movieToSelection = MovieToSelectionNoIDDTOMapper.map(movieToSelectionNoIDDTO);
                    MovieToSelectionRepository.addMovieToSelection(movieToSelection);
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteMovie(Movie movie) {
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MOVIE)){
            preparedStatement.setInt(1, movie.getId());
            if(movie.getSelections() != null && !movie.getSelections().isEmpty()){
                MovieToSelectionRepository.deleteByMovieId(movie.getId());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static void addMovieSelectionList(Movie movie) {
        List<Integer> newSelections = movie.getSelections().stream()
                .map(Selection::getId)
                .collect(Collectors.toList());
        List<Integer> currentSelections = MovieToSelectionRepository.findByMovieId(movie.getId()).stream()
                .map(MovieToSelection::getSelectionId)
                .collect(Collectors.toList());

        for (int i : newSelections) {
            if (currentSelections.contains(i)) {
                newSelections.remove(i);
            }
        }

        for(int i : newSelections) {
            MovieToSelection movieToSelection = new MovieToSelection(null, movie.getId(), i);
            MovieToSelectionRepository.addMovieToSelection(movieToSelection);
        }

    }

    public static List<Movie> getAllSerials(){
        List<Movie> serials = new ArrayList<>();
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SERIALS)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                serials.add(createMovie(resultSet));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return serials;

    }
    private static Movie createMovie(ResultSet resultSet) throws SQLException {
        Director director = DirectorRepository.findById(resultSet.getInt("director_id"));
        /*List<MovieToSelection> selectionsIds = MovieToSelectionRepository.findByMovieId(resultSet.getInt("id"));
        List<Selection> selection = selectionsIds.stream()
                .map(MovieToSelection::getSelectionId)
                .map(SelectionRepository::findSelectionById)
                .collect(Collectors.toList());*/
        Movie movie = new Movie(resultSet.getInt("id"), resultSet.getString("title"),resultSet.getInt("year"), resultSet.getBoolean("isserial"), director, null);
        return movie;
    }
}
