package Servlet;

import DTO.MovieInDTO;
import DTO.MovieOutDTO;
import DTO.MovieUpdateDTO;
import Service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet (urlPatterns = {"/movie/*"})
public class MovieServlet extends HttpServlet {
    private Gson gson = new Gson();
    private ObjectMapper objectMapper;

    public MovieServlet() {
        this.objectMapper = new ObjectMapper();
    }




    private static String getJson(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader postData = req.getReader();
        String line;
        while ((line = postData.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] pathPart =req.getPathInfo().split("/");
        if("all".equals(pathPart[1])){
            List<MovieOutDTO> movies = MovieService.findAll();
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(movies));
        } if ("serials".equals(pathPart[1])){
            List<MovieOutDTO> serials = MovieService.findAllSerials();
            resp.setContentType("application/json");
            if(serials!=null){
                resp.getWriter().write(gson.toJson(serials));
            } else { resp.getWriter().write("No serials"); }
        }else {
            int movieId = Integer.parseInt(pathPart[1]);
            MovieOutDTO movie = MovieService.findById(movieId);
            resp.setContentType("application/json");
            if(movie!=null) {
                resp.getWriter().write(gson.toJson(movie));
            } else resp.getWriter().write("No such movie");
        }

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = getJson(req);
        MovieInDTO movie = gson.fromJson(json, MovieInDTO.class);
        MovieService.add(movie);
        List<MovieOutDTO> movies = MovieService.findAll();
        resp.getWriter().write(gson.toJson(movies));
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = getJson(req);
        MovieUpdateDTO movie = gson.fromJson(json, MovieUpdateDTO.class);
        MovieService.update(movie);
        List<MovieOutDTO> movies = MovieService.findAll();
        resp.getWriter().write(gson.toJson(movies));
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = getJson(req);
        MovieUpdateDTO movie = gson.fromJson(json, MovieUpdateDTO.class);
        MovieService.delete(movie);
        List<MovieOutDTO> movies = MovieService.findAll();
        resp.getWriter().write(gson.toJson(movies));
    }
}
