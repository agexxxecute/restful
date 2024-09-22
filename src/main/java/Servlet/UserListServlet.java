package Servlet;

import Entity.User;
import Repository.UserRepository;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = { "/users/*"})
public class UserListServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] pathPart = req.getPathInfo().split("/");
        if("all".equals(pathPart[1])) {
            List<User> users = UserRepository.getAllUsers();
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(users));
        } else{
            int userId = Integer.parseInt(pathPart[1]);
            User user = UserRepository.findById(userId);
            resp.setContentType("application/json");
            if(user != null){
                resp.getWriter().write(gson.toJson(user));
            } else resp.getWriter().write("Not found");
        }

    }
}
