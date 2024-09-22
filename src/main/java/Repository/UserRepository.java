package Repository;

import DB.DBUtil;
import Entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ?";

    public static List<User> getAllUsers(){
        List<User> users = new ArrayList<User>();
        try(Connection connection = DBUtil.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users")){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                users.add(new User(id, username, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static User findById(int id){
        User user = null;
        try (Connection connection = DBUtil.getConnection();
        PreparedStatement ps = connection.prepareStatement(FIND_USER_BY_ID)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                user = new User(id, rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }
}
