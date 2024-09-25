package Repository.Impl;

import DB.DBUtil;
import Entity.Director;
import Repository.DirectorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DirectorRepositoryImpl implements DirectorRepository {

    private static DirectorRepository instance;

    private static String FIND_ALL_DIRECTORS = "SELECT * FROM director";
    private static String FIND_DIRECTOR_BY_ID = "SELECT * FROM director WHERE id = ?";
    private static String ADD_DIRECTOR = "INSERT INTO director(firstname, lastname) VALUES(?, ?)";
    private static String UPDATE_DIRECTOR = "UPDATE director SET firstname = ?, lastname = ? WHERE id = ?";
    private static String DELETE_DIRECTOR_BY_ID = "DELETE FROM director WHERE id = ?";

    public static DirectorRepository getInstance() {
        if (instance == null) {
            instance = new DirectorRepositoryImpl();
        }
        return instance;
    }

    public List<Director> findAll() {
        List<Director> directors = new ArrayList<>();
        try(Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_DIRECTORS)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                directors.add(createDirector(resultSet));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return directors;
    }

    public Director addDirector(Director director) {
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_DIRECTOR, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, director.getFirstName());
            preparedStatement.setString(2, director.getLastName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                director = new Director(resultSet.getInt("id"), resultSet.getString("firstname"), resultSet.getString("lastname") );
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return director;
    }

    public Director updateDirector(Director director) {
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DIRECTOR)){
            preparedStatement.setString(1, director.getFirstName());
            preparedStatement.setString(2, director.getLastName());
            preparedStatement.setInt(3, director.getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                director = new Director(resultSet.getInt("id"), resultSet.getString("firstname"), resultSet.getString("lastname"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return director;
    }

    public boolean deleteDirectorById(int id) {
        boolean result = false;
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DIRECTOR_BY_ID)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public Director findById(int id) {
        Director director = null;
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_DIRECTOR_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                director = createDirector(resultSet);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return director;
    }

    private Director createDirector(ResultSet resultSet) throws SQLException {
        Director director = new Director(resultSet.getInt("id"), resultSet.getString("firstname"), resultSet.getString("lastname"));
        return director;
    }
}
